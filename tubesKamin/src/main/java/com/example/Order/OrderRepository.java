package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Menyimpan data pesanan ke dalam database
    public void saveOrder(String menu, int jumlah, String nama, String phone) {
        String sql = "INSERT INTO orders (menu, jumlah, nama, phone, order_date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql, menu, nama, jumlah, phone);
    }
}
