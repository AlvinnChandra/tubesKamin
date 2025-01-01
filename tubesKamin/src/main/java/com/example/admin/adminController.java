package com.example.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.login.LoginService;

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

    @PostMapping("/order")
    public String handleOrder(
        @RequestParam Long id_user,
        @RequestParam Map<String, String> allRequestParams,
        Model model){
        for(String key : allRequestParams.keySet()) {
            if (key.startsWith("quantity_")) {
                String menuName = key.substring("quantity_".length());
                int quantity = Integer.parseInt(allRequestParams.get(key));
                
                // Save order to database
                DataTransaksi order = new DataTransaksi();
                order.setId_user(id_user);
                order.setMenu(menuName);
                order.setJumlah(quantity);
                
                // Call the repository method to save the order
                adminRepository.saveOrder(order);
            }
        }

        return "redirect:/admin/transaksi";
    }

    @GetMapping("/transaksi")
    public String showTransaksiPage(Model model){
        List<DataTransaksi> transaksiList = adminRepository.findAllTransaksi();
        model.addAttribute("transaksiList", transaksiList);
        return "Restoran/transaksi";
    }

    @GetMapping("check-name")
    @ResponseBody
    public ResponseEntity<?> checkName(@RequestParam("name") String name){
        var nama = adminRepository.findName(name);
        if(nama.isPresent()){
            return ResponseEntity.ok(nama.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nama tidak ditemukan");
        }
    }
}
