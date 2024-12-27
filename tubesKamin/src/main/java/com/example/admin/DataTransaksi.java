package com.example.admin;

import lombok.Data;

@Data
public class DataTransaksi {
    private Long no_pesanan;
    private Long id_user;
    private String menu;
    private int jumlah;
}
