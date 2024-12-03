package com.example.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderData {
    private int noPesanan;
    private int idUser;
    private String menu;
    private int jumlah;
}