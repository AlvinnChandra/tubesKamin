package com.example.Order;

import lombok.Data;

@Data
public class OrderItem {
    private String menuItem;
    private int quantity;
    private double price;
}

