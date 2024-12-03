package com.example.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderData {
    private int id;
    private int noPesanan;
    private String menu;
    private int jumlah;
}