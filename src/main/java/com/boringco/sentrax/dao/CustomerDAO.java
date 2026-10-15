package com.boringco.sentrax.dao;
import com.boringco.sentrax.exception.FraudDetectionException;
import com.boringco.sentrax.model.Customer;
import com.boringco.sentrax.util.DBConnection;
import java.sql.*;
import java.util.*;
public class CustomerDAO implements GenericDAO<Customer, Integer> {
    private static Customer map(ResultSet rs) throws SQLException {
        Customer c = new Customer(); c.setId(rs.getInt("id")); c.setName(rs.getString("name"));
        c.setHomeCity(rs.getString("home_city")); c.setAvgAmount(rs.getDouble("avg_amount")); return c;
    }
    @Override public void save(Customer c) {
        String sql = "INSERT INTO customer(name, home_city, avg_amount) VALUES (?,?,?)";
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getName()); ps.setString(2, c.getHomeCity()); ps.setDouble(3, c.getAvgAmount());
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) c.setId(k.getInt(1)); }
        } catch (SQLException e) { throw new FraudDetectionException("Could not save customer", e); }
    }
    @Override public Optional<Customer> findById(Integer id) {
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement("SELECT * FROM customer WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        } catch (SQLException e) { throw new FraudDetectionException("Could not load customer", e); }
    }
    @Override public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement("SELECT * FROM customer ORDER BY name"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new FraudDetectionException("Could not load customers", e); }
        return list;
    }
}
