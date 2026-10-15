package com.boringco.sentrax.dao;
import com.boringco.sentrax.exception.FraudDetectionException;
import com.boringco.sentrax.model.*;
import com.boringco.sentrax.util.DBConnection;
import java.sql.*;
import java.util.*;
public class AlertDAO implements GenericDAO<Alert, Long> {
    private static final String BASE = "SELECT a.*, t.amount, t.risk_score, t.risk_level, t.reasons, c.name AS customer_name "
        + "FROM alert a JOIN txn t ON t.id = a.transaction_id JOIN customer c ON c.id = t.customer_id ";
    private static Alert map(ResultSet rs) throws SQLException {
        Alert a = new Alert(rs.getLong("transaction_id"));
        a.setId(rs.getLong("id")); a.setStatus(rs.getString("status")); a.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        Timestamp r = rs.getTimestamp("resolved_at"); if (r != null) a.setResolvedAt(r.toLocalDateTime());
        a.setCustomerName(rs.getString("customer_name")); a.setAmount(rs.getBigDecimal("amount"));
        a.setRiskScore(rs.getInt("risk_score")); a.setRiskLevel(RiskLevel.valueOf(rs.getString("risk_level"))); a.setReasons(rs.getString("reasons"));
        return a;
    }
    private List<Alert> query(String sql, Object... args) {
        List<Alert> list = new ArrayList<>();
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) ps.setObject(i + 1, args[i]);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } catch (SQLException e) { throw new FraudDetectionException("Alert query failed", e); }
        return list;
    }
    @Override public void save(Alert a) {
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement("INSERT INTO alert(transaction_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, a.getTransactionId()); ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) a.setId(k.getLong(1)); }
        } catch (SQLException e) { throw new FraudDetectionException("Could not save alert", e); }
    }
    @Override public Optional<Alert> findById(Long id) { return query(BASE + "WHERE a.id = ?", id).stream().findFirst(); }
    @Override public List<Alert> findAll() { return query(BASE + "ORDER BY a.created_at DESC"); }
    public List<Alert> findOpen() { return query(BASE + "WHERE a.status = 'OPEN' ORDER BY t.risk_score DESC, a.created_at DESC"); }
    public int countOpen() {
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM alert WHERE status = 'OPEN'"); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { throw new FraudDetectionException("Could not count alerts", e); }
    }
    public void resolve(long id) {
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement("UPDATE alert SET status = 'RESOLVED', resolved_at = NOW() WHERE id = ?")) {
            ps.setLong(1, id); ps.executeUpdate();
        } catch (SQLException e) { throw new FraudDetectionException("Could not resolve alert", e); }
    }
}
