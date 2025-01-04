package com.example.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DataTransaksi {
    private Long no_pesanan;
    private Long id_user;
    private String namaPelanggan;
    private LocalDateTime tanggal;
    private List<OrderItem> orderItems = new ArrayList<>();
    private Boolean status;

    public void addOrderItem(String menuName, int jumlah) {
        this.orderItems.add(new OrderItem(menuName, jumlah));
    }

    @Data
    public static class OrderItem {
        private String menuName;
        private int jumlah;

        public OrderItem(String menuName, int jumlah) {
            this.menuName = menuName;
            this.jumlah = jumlah;
        }
    }
}
