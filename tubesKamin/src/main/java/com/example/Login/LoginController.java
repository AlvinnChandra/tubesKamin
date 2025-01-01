package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showMenuPage() {
        return "/Login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model){
        LoginData user = loginService.login(username, password);
        if(user != null){
            String role = user.getPeran();
            return switch (role) {
                case "admin" -> "redirect:/admin/menu";
                default -> "redirect:/login";
            };
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "/Login";
        }
    }
}