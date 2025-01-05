package com.example.admin;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.example.QRCodeGenerator;
import com.example.QRCodeScanner;

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
    public String showOrderPage(Model model) {
        List<DataInventories> menuItem = adminRepository.findAllInventories();
        model.addAttribute("menuItems", menuItem);
        return "/Restoran/orderPage";
    }

    @PostMapping("/order")
    public String handleOrder(@RequestParam Long id_user,
            @RequestParam Map<String, String> allRequestParams,
            Model model) {
        DataTransaksi mainOrder = new DataTransaksi();
        mainOrder.setId_user(id_user);

        // Simpan data ke tabel orders
        Long noPesanan = adminRepository.saveMainOrder(mainOrder);

        // Iterasi untuk menyimpan detail pesanan ke order_items
        for (String key : allRequestParams.keySet()) {
            if (key.startsWith("quantity_")) {
                String menuName = key.substring("quantity_".length());
                int quantity = Integer.parseInt(allRequestParams.get(key));

                // Cari id_menu berdasarkan nama menu
                Long idMenu = adminRepository.findMenuIdByName(menuName);

                // Simpan ke tabel order_items
                adminRepository.saveOrderItem(noPesanan, idMenu, quantity);
            }
        }

        //Buat QR COde
        String filename = "C:\\kuliahDion\\kamin\\tubes\\tubesKamin\\tubesKamin\\public.key"; //GANTI INI
        try {
            PublicKey publicKey = QRCodeGenerator.loadPublicKey(filename);
            String qrCodeString = QRCodeGenerator.generateQRCodeString(noPesanan, allRequestParams);
            String encryptString = QRCodeGenerator.encrypt(qrCodeString, publicKey);
            String qrCodePath = "QRCode/QR" + noPesanan + ".png";
            QRCodeGenerator.generateQRCode(encryptString, qrCodePath);
            adminRepository.saveQRCode(noPesanan, qrCodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/transaksi";
    }

    @GetMapping("/transaksi")
    public String showTransaksiPage(Model model) {
        List<DataTransaksi> transaksiList = adminRepository.findAllTransaksi();

        // Mengelompokkan transaksi berdasarkan no_pesanan
        Map<Long, DataTransaksi> groupedTransaksi = transaksiList.stream()
                .collect(Collectors.toMap(
                        DataTransaksi::getNo_pesanan,
                        transaksi -> transaksi,
                        (existing, replacement) -> {
                            existing.getOrderItems().addAll(replacement.getOrderItems());
                            return existing;
                        }));

        // Menambahkan daftar transaksi yang sudah digabungkan berdasarkan no_pesanan
        model.addAttribute("transaksiList", new ArrayList<>(groupedTransaksi.values()));

        return "Restoran/transaksi";
    }

    @PostMapping("/scan-qr-code")
    @ResponseBody
    public ResponseEntity<?> scanQRCode(@RequestParam Long noPesanan) {
        DataTransaksi transaksi = adminRepository.findTransaksiByNoPesanan(noPesanan);
        if(transaksi != null){
            String qrCodePath = "C:\\kuliahDion\\kamin\\tubes\\tubesKamin\\tubesKamin\\QRCode\\QR" + noPesanan + ".png";
            try {
                String encryptedData = QRCodeScanner.readQRCode(qrCodePath);
                String filename = "C:\\kuliahDion\\kamin\\tubes\\tubesKamin\\tubesKamin\\private.key"; //GANTI INI
                PrivateKey privateKey = QRCodeScanner.loadPrivateKey(filename);
                String decryptedData = QRCodeScanner.decrypt(encryptedData, privateKey);

                // Map<String, String> orderItemsMap = transaksi.getOrderItems().stream()
                //     .collect(Collectors.toMap(
                //             item -> "quantity_" + item.getMenuName(),
                //             item -> String.valueOf(item.getJumlah())
                //     ));
                // String qrCodeString = QRCodeGenerator.generateQRCodeString(noPesanan, orderItemsMap);
                // if(decryptedData.equals(qrCodeString)){
                //     adminRepository.updateStatusTransaksi(noPesanan, false);
                //     return ResponseEntity.ok().body(Map.of("success", true, "message", "QR Code valid"));
                // } else {
                //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "QR Code tidak valid"));
                // }
                
                adminRepository.updateStatusTransaksi(noPesanan, false);
                return ResponseEntity.ok().body(Map.of("success", true));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Gagal scan QR Code"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Transaksi tidak ditemukan"));
        } 
    }
    
    @GetMapping("check-name")
    @ResponseBody
    public ResponseEntity<?> checkName(@RequestParam("name") String name) {
        var nama = adminRepository.findName(name);
        if (nama.isPresent()) {
            return ResponseEntity.ok(nama.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nama tidak ditemukan");
        }
    }
}
