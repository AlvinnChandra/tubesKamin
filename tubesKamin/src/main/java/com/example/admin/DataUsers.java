package com.example.admin;

import lombok.Data;

@Data
public class DataUsers {
    private Long id_user;
    private String nama;
    private String passwords;
    private String no_telepon;
    private String peran;
}
