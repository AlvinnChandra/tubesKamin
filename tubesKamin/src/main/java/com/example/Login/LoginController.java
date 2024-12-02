package com.example.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Menampilkan halaman login
    @GetMapping("/login")
    public String showLoginForm() {
        return "/Restoran/login"; 
    }

    // Menangani proses login
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            Map<String, Object> user = jdbcTemplate.queryForMap(sql, username, password);

            String role = (String) user.get("role");

            if ("admin".equals(role)) {
                return "redirect:/menu";
            } else {
                model.addAttribute("error", "Role tidak ditemukan");
            }
        } catch (Exception e) {

            model.addAttribute("error", "Nama pengguna atau kata sandi tidak valid");
        }
        return "login";
    }

    // Halaman menu
    @GetMapping("/menu")
    public String menuPage() {
        return "/Restoran/menu";
    }
}
