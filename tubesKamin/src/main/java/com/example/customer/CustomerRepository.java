package com.example.customer;

import java.util.List;

import com.example.admin.DataTransaksi;

public interface CustomerRepository {
    List<DataTransaksi> findTransaksiByUser (Long id_user);
}
