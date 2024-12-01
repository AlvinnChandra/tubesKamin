package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/order")
    public String placeOrder(@RequestParam Map<String, String> params) {
        String name = params.get("name");
        String phone = params.get("phone");

        // Menyimpan pesanan untuk setiap menu yang dipilih
        for (String key : params.keySet()) {
            if (key.startsWith("quantity_")) {
                String menu = key.replace("quantity_", "");
                int quantity = Integer.parseInt(params.get(key));

                // Simpan ke database
                orderRepository.saveOrder(menu, quantity, name, phone);
            }
        }

        return "orderSuccess"; // Ganti dengan nama view yang ingin ditampilkan setelah pemesanan berhasil
    }
}
