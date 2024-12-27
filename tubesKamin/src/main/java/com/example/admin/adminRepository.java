package com.example.admin;

import java.util.List;
import java.util.Optional;

public interface adminRepository {
    List<DataInventories> findAllInventories();
    List<DataTransaksi> findAllTransaksi();
    Optional<DataUsers> findName(String name);
    void saveOrder(DataTransaksi order);
}
