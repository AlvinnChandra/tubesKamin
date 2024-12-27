package com.example.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private adminRepository adminRepository;

    @GetMapping("/menu")
    public String showMenuPage(Model model) {
        List<DataInventories> menuItem = adminRepository.findAllInventories();
        model.addAttribute("menuItems", menuItem);
        return "/Restoran/menu";
    }

    @GetMapping("/order")
    public String showOrderPage(Model model){
        List<DataInventories> menuItem = adminRepository.findAllInventories();
        model.addAttribute("menuItems", menuItem);
        return "/Restoran/orderPage";
    }

    @GetMapping("/transaksi")
    public String showTransaksiPage(Model model){
        List<DataTransaksi> transaksiList = adminRepository.findAllTransaksi();
        model.addAttribute("transaksiList", transaksiList);
        return "Restoran/transaksi";
    }
}
