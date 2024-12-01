package com.example.Order;

import lombok.Data;

@Data
public class Order {
    private int id;
    private String menu;
    private String jumlah;
    private String nama;
    private String phone;
    private String oderDate;
}