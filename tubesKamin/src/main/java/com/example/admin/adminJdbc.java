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
    public List<DataInventories> findAll(){
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
}
