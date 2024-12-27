package com.example.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class adminJdbc implements adminRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DataInventories> findAllInventories(){
        String sql = "SELECT * FROM inventories";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DataInventories item = new DataInventories();
            item.setId_menu(rs.getLong("id_menu"));
            item.setNama(rs.getString("nama"));
            item.setDescription(rs.getString("description"));
            item.setPrice(rs.getBigDecimal("price"));
            item.setStock(rs.getInt("stock"));
            return item;
        });
    }

    @Override
    public List<DataTransaksi> findAllTransaksi(){
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DataTransaksi transaksi = new DataTransaksi();
            transaksi.setNo_pesanan(rs.getLong("no_pesanan"));
            transaksi.setId_user(rs.getLong("id_user"));
            transaksi.setMenu(rs.getString("menu"));
            transaksi.setJumlah(rs.getInt("jumlah"));
            return transaksi;
        });
    }
}
