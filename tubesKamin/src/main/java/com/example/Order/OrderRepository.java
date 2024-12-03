package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addOrder(OrderData order) {
        String sql = "INSERT INTO orders (id_user, menu, jumlah) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, order.getIdUser(), order.getMenu(), order.getJumlah());
    }

    public List<OrderData> getOrdersByUserId(int idUser) {
        String sql = "SELECT * FROM orders WHERE id_user = ?";
        return jdbcTemplate.query(sql, this::mapRowToOrder, idUser); // Menggunakan mapRowToOrder
    }

    public List<OrderData> getAllOrders() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, this::mapRowToOrder); // Menggunakan mapRowToOrder
    }

    private OrderData mapRowToOrder(ResultSet resultSet, int rowNum) throws SQLException {
        return new OrderData(
                resultSet.getInt("no_pesanan"),
                resultSet.getInt("id_user"),
                resultSet.getString("menu"),
                resultSet.getInt("jumlah"));
    }
}
