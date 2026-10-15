package com.boringco.sentrax.util;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
public final class DBConnection {
    private static final Properties P = new Properties();
    static {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("db.properties not found on classpath");
            P.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) { throw new ExceptionInInitializerError(e); }
    }
    private DBConnection() { }
    public static Connection get() throws SQLException {
        return DriverManager.getConnection(P.getProperty("db.url"), P.getProperty("db.user"), P.getProperty("db.password"));
    }
}
