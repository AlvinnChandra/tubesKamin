package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order")
    public String showOrderPage() {
        return "/Restoran/order"; // Mengarah ke order.html di dalam src/main/resources/templates
    }

    @PostMapping("/order")
    public String createOrder(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam Map<String, String> allParams) {
        // 1. Tambahkan pengguna (jika belum ada)
        UserData user = new UserData(0, name, phone);
        userRepository.addUser(user);

        // 2. Ambil ID user berdasarkan nama dan nomor telepon
        UserData savedUser = userRepository.getUserByPhone(phone);

        // 3. Simpan pesanan untuk setiap menu yang dipilih
        allParams.forEach((key, value) -> {
            if (key.startsWith("quantity_")) {
                String menuName = key.replace("quantity_", "");
                int quantity = Integer.parseInt(value);
                OrderData order = new OrderData(0, savedUser.getIdUser(), menuName, quantity);
                orderRepository.addOrder(order);
            }
        });

        return "Pesanan berhasil dibuat!";
    }
}
