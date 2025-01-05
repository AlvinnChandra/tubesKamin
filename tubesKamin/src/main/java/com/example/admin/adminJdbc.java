package com.example.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class adminJdbc implements adminRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DataInventories> findAllInventories() {
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
    public List<DataTransaksi> findAllTransaksi() {
        String sql = """
                SELECT o.no_pesanan, o.id_user, u.nama AS namaPelanggan, o.tanggal,
                       i.nama AS namaMenu, oi.jumlah, o.status
                FROM orders o
                JOIN users u ON o.id_user = u.id_user
                JOIN order_items oi ON o.no_pesanan = oi.no_pesanan
                JOIN inventories i ON oi.id_menu = i.id_menu
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
        });
    }

    @Override
    public Optional<DataUsers> findName(String name) {
        String sql = "SELECT id_user, nama, no_telepon, peran FROM users WHERE nama = ?";
        List<DataUsers> listData = jdbcTemplate.query(sql, ps -> ps.setString(1, name),
                new BeanPropertyRowMapper<>(DataUsers.class));
        return listData.isEmpty() ? Optional.empty() : Optional.of(listData.get(0));
    }

    @Override
    public Long saveMainOrder(DataTransaksi order) {
        String sql = "INSERT INTO orders (id_user, status) VALUES (?, ?) RETURNING no_pesanan";
        return jdbcTemplate.queryForObject(sql, Long.class, order.getId_user(), true);
    }

    @Override
    public void saveOrderItem(Long noPesanan, Long idMenu, int jumlah) {
        // Menambahkan implementasi untuk menyimpan order item
        String sql = "INSERT INTO order_items (no_pesanan, id_menu, jumlah) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, noPesanan, idMenu, jumlah);
    }

    @Override
    public Long findMenuIdByName(String menuName) {
        String sql = "SELECT id_menu FROM inventories WHERE nama = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, menuName);
    }

    @Override
    public void saveQRCode(Long noPesanan, String qrCodePath){
        String sql = "UPDATE orders SET qr_code = ? WHERE no_pesanan = ?";
        try {
            jdbcTemplate.update(sql, new Object[]{Files.readAllBytes(Paths.get(qrCodePath)), noPesanan});    
        } catch (Exception e) {
            System.out.println("Error membaca file QR Code: " + e.getMessage());
        }
        
    }

    @Override
    public void updateStatusTransaksi(Long noPesanan, boolean status){
        String sql = "UPDATE orders SET status = ? WHERE no_pesanan = ?";
        jdbcTemplate.update(sql, status, noPesanan);
    }

    @Override
    public DataTransaksi findTransaksiByNoPesanan(Long noPesanan){
        String sql = """
        SELECT o.no_pesanan, o.id_user, u.nama AS namaPelanggan, o.tanggal,
               i.nama AS namaMenu, oi.jumlah
        FROM orders o
        JOIN users u ON o.id_user = u.id_user
        JOIN order_items oi ON o.no_pesanan = oi.no_pesanan
        JOIN inventories i ON oi.id_menu = i.id_menu
        WHERE o.no_pesanan = ?
        ORDER BY o.no_pesanan;
        """;

        List<DataTransaksi> transaksiList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            DataTransaksi transaksi = new DataTransaksi();
            transaksi.setNo_pesanan(rs.getLong("no_pesanan"));
            transaksi.setId_user(rs.getLong("id_user"));
            transaksi.setNamaPelanggan(rs.getString("namaPelanggan"));
            transaksi.setTanggal(rs.getTimestamp("tanggal").toLocalDateTime());
            transaksi.addOrderItem(rs.getString("namaMenu"), rs.getInt("jumlah"));
            return transaksi;
        }, noPesanan);

        // Mengelompokkan transaksi berdasarkan no_pesanan
        Map<Long, DataTransaksi> groupedTransaksi = transaksiList.stream()
                .collect(Collectors.toMap(
                        DataTransaksi::getNo_pesanan,
                        transaksi -> transaksi,
                        (existing, replacement) -> {
                            existing.getOrderItems().addAll(replacement.getOrderItems());
                            return existing;
                        }));

        // Mengembalikan transaksi yang sudah digabungkan berdasarkan no_pesanan
        return groupedTransaksi.values().stream().findFirst().orElse(null);
    }
}
