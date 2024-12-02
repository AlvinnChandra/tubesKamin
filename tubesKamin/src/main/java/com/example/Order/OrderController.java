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
        String nama = params.get("name");
        String phone = params.get("phone");

        // Simpan data utama ke tabel `orders` dan dapatkan ID pesanan
        int orderId = orderRepository.saveOrder(nama, phone);

        // Simpan data menu ke tabel `order_details`
        for (String key : params.keySet()) {
            if (key.startsWith("quantity_")) {
                String menu = key.replace("quantity_", "");
                int jumlah = Integer.parseInt(params.get(key));

                // Simpan detail menu ke tabel `order_details`
                orderRepository.saveOrderDetails(orderId, menu, jumlah);
            }
        }

        return "/Restoran/order"; // Redirect ke halaman konfirmasi
    }
}
