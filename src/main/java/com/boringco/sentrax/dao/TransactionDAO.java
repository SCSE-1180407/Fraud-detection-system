package com.boringco.sentrax.dao;
import com.boringco.sentrax.exception.FraudDetectionException;
import com.boringco.sentrax.model.*;
import com.boringco.sentrax.util.DBConnection;
import java.sql.*;
import java.util.*;
public class TransactionDAO implements GenericDAO<Transaction, Long> {
    private static final String BASE = "SELECT t.*, c.name AS customer_name FROM txn t JOIN customer c ON c.id = t.customer_id ";
    private static Transaction map(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getLong("id")); t.setCustomerId(rs.getInt("customer_id")); t.setCustomerName(rs.getString("customer_name"));
        t.setAmount(rs.getBigDecimal("amount")); t.setMerchant(rs.getString("merchant")); t.setLocation(rs.getString("location"));
        t.setDeviceId(rs.getString("device_id")); t.setTxnTime(rs.getTimestamp("txn_time").toLocalDateTime());
        t.setRiskScore(rs.getInt("risk_score")); t.setReasons(rs.getString("reasons"));
        String lv = rs.getString("risk_level"); if (lv != null) t.setRiskLevel(RiskLevel.valueOf(lv));
        return t;
    }
    private List<Transaction> query(String sql, Object... args) {
        List<Transaction> list = new ArrayList<>();
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) ps.setObject(i + 1, args[i]);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } catch (SQLException e) { throw new FraudDetectionException("Transaction query failed", e); }
        return list;
    }
    @Override public void save(Transaction t) {
        String sql = "INSERT INTO txn(customer_id, amount, merchant, location, device_id, txn_time, risk_score, risk_level, reasons) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getCustomerId()); ps.setBigDecimal(2, t.getAmount()); ps.setString(3, t.getMerchant());
            ps.setString(4, t.getLocation()); ps.setString(5, t.getDeviceId()); ps.setTimestamp(6, Timestamp.valueOf(t.getTxnTime()));
            ps.setInt(7, t.getRiskScore()); ps.setString(8, t.getRiskLevel().name()); ps.setString(9, t.getReasons());
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) t.setId(k.getLong(1)); }
        } catch (SQLException e) { throw new FraudDetectionException("Could not save transaction", e); }
    }
    @Override public Optional<Transaction> findById(Long id) { return query(BASE + "WHERE t.id = ?", id).stream().findFirst(); }
    @Override public List<Transaction> findAll() { return query(BASE + "ORDER BY t.txn_time DESC"); }
    public List<Transaction> findRecentByCustomer(int customerId, int limit) {
        return query(BASE + "WHERE t.customer_id = ? ORDER BY t.txn_time DESC LIMIT ?", customerId, limit);
    }
    public Map<RiskLevel, Integer> countByLevel() {
        Map<RiskLevel, Integer> m = new EnumMap<>(RiskLevel.class);
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement("SELECT risk_level, COUNT(*) n FROM txn GROUP BY risk_level"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) m.put(RiskLevel.valueOf(rs.getString(1)), rs.getInt(2));
        } catch (SQLException e) { throw new FraudDetectionException("Could not count transactions", e); }
        return m;
    }
}
