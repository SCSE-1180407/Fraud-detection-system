package com.boringco.sentrax.dao;
import com.boringco.sentrax.exception.FraudDetectionException;
import com.boringco.sentrax.model.AppUser;
import com.boringco.sentrax.util.DBConnection;
import java.sql.*;
import java.util.Optional;
public class UserDAO {
    public Optional<AppUser> findByUsername(String username) {
        try (Connection con = DBConnection.get(); PreparedStatement ps = con.prepareStatement("SELECT * FROM app_user WHERE username = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                AppUser u = new AppUser(); u.setId(rs.getInt("id")); u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash")); u.setRole(rs.getString("role")); return Optional.of(u);
            }
        } catch (SQLException e) { throw new FraudDetectionException("Could not load user", e); }
    }
}
