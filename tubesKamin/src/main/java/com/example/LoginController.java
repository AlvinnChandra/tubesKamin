package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showMenuPage() {
        // Mengembalikan nama file HTML tanpa ekstensi yang ada di folder templates
        return "/Restoran/login";
    }
}