package com.example.Order;


import lombok.Data;

@Data
public class OrderDetail {
    private int id;
    private int orderId;
    private String menu;
    private int jumlah;
}
