package com.example.admin;

import java.util.List;
import java.util.Optional;

public interface adminRepository {
    List<DataInventories> findAllInventories();

    List<DataTransaksi> findAllTransaksi();

    Optional<DataUsers> findName(String name);

    Long saveMainOrder(DataTransaksi order);

    void saveOrderItem(Long noPesanan, Long idMenu, int jumlah);

    Long findMenuIdByName(String menuName);
}
