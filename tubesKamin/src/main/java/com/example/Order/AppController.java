package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/order")
    public String createOrder(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam Map<String, String> allParams) {

        // 1. Tambahkan pengguna (jika belum ada)
        UserData user = new UserData(0, name, phone);
        userRepository.addUser(user);

        // 2. Ambil ID user berdasarkan nomor telepon
        user = userRepository.getUserByPhone(phone);

        // 3. Tambahkan header pesanan (OrderHeaderData) dan dapatkan noPesanan
        int noPesanan = orderRepository.addOrderHeader(new OrderHeaderData(0, user.getIdUser()));

        if (noPesanan <= 0) {
            return "Terjadi kesalahan saat memproses pesanan. Coba lagi.";
        }

        // 4. Simpan pesanan untuk setiap menu yang dipilih
        allParams.forEach((key, value) -> {
            if (key.startsWith("quantity_")) {
                String menuName = key.replace("quantity_", "");
                int quantity = Integer.parseInt(value);

                // Menyimpan item pesanan (menu dan jumlah) dengan noPesanan yang sama
                orderRepository.addOrderItem(noPesanan, menuName, quantity);
            }
        });

        return "Pesanan berhasil dibuat!";
    }


    @GetMapping("/order/{noPesanan}")
    public List<OrderData> getOrderDetails(@PathVariable int noPesanan) {
        return orderRepository.getOrderItemsByOrderId(noPesanan);
    }
}

