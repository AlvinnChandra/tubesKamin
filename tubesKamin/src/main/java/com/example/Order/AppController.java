package com.example.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.QRCodeGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @PostMapping("/order")
    public String createOrder(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam Map<String, String> allParams,
            Model model) {

        try {
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
            StringBuilder orderDetails = new StringBuilder();
            allParams.forEach((key, value) -> {
                if (key.startsWith("quantity_")) {
                    String menuName = key.replace("quantity_", "");
                    int quantity = Integer.parseInt(value);

                    // Menyimpan item pesanan (menu dan jumlah) dengan noPesanan yang sama
                    orderDetails.append(menuName);
                    orderRepository.addOrderItem(noPesanan, menuName, quantity);
                }
            });

            //Generate QR buat pesana
            String qrString = qrCodeGenerator.generateQRCodeString(Integer.toString(noPesanan), orderDetails.toString());

            PublicKey publicKey = qrCodeGenerator.loadPublicKey("public.key");

            String encryptedQRCode = qrCodeGenerator.encrypt(qrString, publicKey);

            String qrCodeDirectory = "src/main/resources/static/QRCode";
            Path path = Paths.get(qrCodeDirectory);
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }

            String qrFileName = "QRCode_" + noPesanan + ".png";
            String qrCodePath = qrCodeDirectory + "/" + qrFileName;

            QRCodeGenerator.generateQRCode(encryptedQRCode, qrCodePath);


            model.addAttribute("orderNumbers", noPesanan);
            model.addAttribute("orderDetails", orderDetails.toString());
            model.addAttribute("qrCodePath", qrFileName);

            return "/Pembeli/result";
        } catch(Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan saat memproses pesanan: " + e.getMessage();
        }
    }


    @GetMapping("/order")
    public List<OrderData> getOrderDetails(@PathVariable int noPesanan) {
        return orderRepository.getOrderItemsByOrderId(noPesanan);
    }
}


