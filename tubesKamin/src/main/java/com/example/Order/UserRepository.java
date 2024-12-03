package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addUser(UserData user) {
        String sql = "INSERT INTO users (nama, no_telepon) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getNama(), user.getNoTelepon());
    }

    public List<UserData> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, this::mapRowToUser); // Menggunakan mapRowToUser
    }

    public UserData getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id_user = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToUser, id); // Menggunakan mapRowToUser
    }

    public UserData getUserByPhone(String phone) {
        String sql = "SELECT * FROM users WHERE no_telepon = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToUser, phone);
    }

    private UserData mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new UserData(
                resultSet.getInt("id_user"),
                resultSet.getString("nama"),
                resultSet.getString("no_telepon"));
    }
}
