package com.example.login;

import lombok.Data;

@Data
public class LoginData {
    private Long id_user;
    private String nama;
    private String passwords;
    private String no_telepon;
    private String peran;
}
