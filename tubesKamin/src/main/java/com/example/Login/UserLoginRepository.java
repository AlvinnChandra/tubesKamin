package com.example.Login;

import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
public class UserLoginRepository {

    private Connection connection;

    public UserLoginRepository() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/manpro",
                    "postgres",
                    "7november2003");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public UserLogin findByUsername(String username) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT id, username, password, role FROM user_login WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserLogin user = new UserLogin();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
