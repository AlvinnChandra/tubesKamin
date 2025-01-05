package com.example.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.QRCodeGenerator;
import com.example.Order.OrderHeaderData;
import com.example.Order.OrderRepository;
import com.example.Order.UserData;
import com.example.Order.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    // Menampilkan halaman login
    @GetMapping("/login")
    public String showLoginForm() {
        return "/Restoran/login"; 
    }

    // Menangani proses login
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        String sql = "SELECT * FROM login WHERE username = ? AND password = ?";
        try {
            Map<String, Object> user = jdbcTemplate.queryForMap(sql, username, password);

            String role = (String) user.get("role");

            if ("admin".equals(role)) {
                return "redirect:/menu";
            } else {
                model.addAttribute("error", "Role tidak ditemukan");
            }
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("error", "Nama pengguna atau kata sandi tidak valid");
            e.printStackTrace();
        } catch (Exception e){
            model.addAttribute("error", "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
        return "/Restoran/login";
    }

    // Halaman menu
    @GetMapping("/menu")
    public String menuPage() {
        return "/Restoran/menu";
    }

    @PostMapping("/menu")
    public String createOrder(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam Map<String, String> allParams,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            // 1. Tambahkan pengguna (jika belum ada)
            UserData user = new UserData(0, name, phone);
            userRepository.addUser(user);

            // 2. Ambil ID user berdasarkan nomor telepon
            user = userRepository.getUserByPhone(phone);

            // 3. Tambahkan header pesanan (OrderHeaderData) dan dapatkan noPesanan
            int noPesanan = orderRepository.addOrderHeader(new OrderHeaderData(0, user.getIdUser()));

            if (noPesanan <= 0) {
                redirectAttributes.addFlashAttribute("error", "Terjadi kesalahan");
                return "redirect:/menu";
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

            return "Pembeli/result";
        } catch(Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Terjadi kesalahan");
            return "redirect:/menu";
        }
    }

    @GetMapping("/result")
    public String showResult(){
        return "Pembeli/result";
    }
}
