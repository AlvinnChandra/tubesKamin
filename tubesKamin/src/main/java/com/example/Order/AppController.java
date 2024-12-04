package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Route untuk menampilkan halaman order
    @GetMapping("/order")
    public String showOrderPage() {
        return "/Restoran/order";  // Menampilkan order.html dari templates/Restoran/order.html
    }

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
            return "redirect:/error"; // Redirect ke halaman error jika gagal
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

        // Redirect ke halaman order
        return "forward:/Restoran/order";  // Gunakan forward jika ingin menggunakan file HTML dari templates
    }

    @GetMapping("/order/{noPesanan}")
    public List<OrderData> getOrderDetails(@PathVariable int noPesanan) {
        return orderRepository.getOrderItemsByOrderId(noPesanan);
    }
}
