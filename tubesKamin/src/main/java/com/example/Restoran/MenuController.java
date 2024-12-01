package com.example.Restoran;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/menu")
    public String showMenuPage() {
        // Mengembalikan nama file HTML tanpa ekstensi yang ada di folder templates
        return "/Restoran/menu";
    }
}
