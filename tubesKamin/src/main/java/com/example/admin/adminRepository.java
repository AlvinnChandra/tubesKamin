package com.example.admin;

import java.util.List;

public interface adminRepository {
    List<DataInventories> findAllInventories();
    List<DataTransaksi> findAllTransaksi();
}
