package com.example.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.admin.DataTransaksi;

@Repository
public class CustomerJdbc implements CustomerRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DataTransaksi> findTransaksiByUser (Long id_user){
        String sql = """
            SELECT o.no_pesanan, o.id_user, u.nama AS namaPelanggan, o.tanggal,
                   i.nama AS namaMenu, oi.jumlah, o.status
            FROM orders o
            JOIN users u ON o.id_user = u.id_user
            JOIN order_items oi ON o.no_pesanan = oi.no_pesanan
            JOIN inventories i ON oi.id_menu = i.id_menu
            WHERE o.id_user = ?
            ORDER BY o.no_pesanan;
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DataTransaksi transaksi = new DataTransaksi();
            transaksi.setNo_pesanan(rs.getLong("no_pesanan"));
            transaksi.setId_user(rs.getLong("id_user"));
            transaksi.setNamaPelanggan(rs.getString("namaPelanggan"));
            transaksi.setTanggal(rs.getTimestamp("tanggal").toLocalDateTime());
            transaksi.addOrderItem(rs.getString("namaMenu"), rs.getInt("jumlah"));
            transaksi.setStatus(rs.getBoolean("status"));
            return transaksi;
        }, id_user);
    }
}
