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

    // Tambahkan header transaksi ke tabel orders
    public int addOrderHeader(OrderHeaderData orderHeader) {
        String sql = "INSERT INTO orders (id_user) VALUES (?) RETURNING no_pesanan";
        return jdbcTemplate.queryForObject(sql, Integer.class, orderHeader.getIdUser());
    }

    // Tambahkan detail transaksi ke tabel order_items
    public void addOrderItem(int noPesanan, String menu, int quantity) {
        String sql = "INSERT INTO order_items (no_pesanan, menu, jumlah) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, noPesanan, menu, quantity);
    }    

    // Ambil semua detail berdasarkan no_pesanan
    public List<OrderData> getOrderItemsByOrderId(int noPesanan) {
        String sql = "SELECT * FROM order_items WHERE no_pesanan = ?";
        return jdbcTemplate.query(sql, this::mapRowToOrderItem, noPesanan);
    }

    // Mapping untuk tabel order_items
    private OrderData mapRowToOrderItem(ResultSet resultSet, int rowNum) throws SQLException {
        return new OrderData(
                resultSet.getInt("id"),
                resultSet.getInt("no_pesanan"),
                resultSet.getString("menu"),
                resultSet.getInt("jumlah"));
    }
}

