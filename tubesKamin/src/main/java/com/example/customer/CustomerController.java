package com.example.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.admin.DataTransaksi;
import com.example.Login.LoginData;


@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @GetMapping("")
    public String homePage(Model model, @SessionAttribute("user") LoginData user){
        List<DataTransaksi> transaksiList = customerRepository.findTransaksiByUser(user.getId_user());

        Map<Long, DataTransaksi> groupedTransaksi = transaksiList.stream()
            .collect(Collectors.toMap(
                    DataTransaksi::getNo_pesanan,
                    transaksi -> transaksi,
                    (existing, replacement) -> {
                        existing.getOrderItems().addAll(replacement.getOrderItems());
                        return existing;
                    }));
        
        model.addAttribute("transaksiList", new ArrayList<>(groupedTransaksi.values()));
        return "/Pembeli/transaksiCust";
    }
}
