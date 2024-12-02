package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Simpan data ke tabel 'orders' dan dapatkan ID pesanan
    public int saveOrder(String nama, String phone) {
        String sql = "INSERT INTO orders (nama, phone, order_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, nama);
            ps.setString(2, phone);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    // Simpan data ke tabel 'order_details'
    public void saveOrderDetails(int orderId, String menu, int jumlah) {
        String sql = "INSERT INTO order_details (order_id, menu, jumlah) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, orderId, menu, jumlah);
    }
}
