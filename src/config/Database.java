package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://ep-empty-water-a40xqwma-pooler.us-east-1.aws.neon.tech:5432/BrasilBurgerDB?sslmode=require";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_jiqRZl1n4UPh";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
