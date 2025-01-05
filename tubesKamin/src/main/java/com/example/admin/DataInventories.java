package com.example.admin;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DataInventories {
    private Long id_menu;
    private String nama;
    private String description;
    private BigDecimal price;
    private int stock;
}
