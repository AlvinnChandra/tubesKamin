package com.example.Pembeli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.QRCodeScanner;

@Controller
@RequestMapping("/pembeli")
public class PembeliController {
    
    @GetMapping("/")
    public String index(){
        return "/Pembeli/index";
    }

    @PostMapping("/scan")
    public String scanQRCode(@RequestParam("file") MultipartFile file, Model model){
        try {
            Path tempFile = Files.createTempFile("qrcode", ".png");
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            //Read dan Decrypted
            String encryptedData = QRCodeScanner.readQRCode(tempFile.toString());
            String decryptedData = QRCodeScanner.decrypt(
                encryptedData, 
                QRCodeScanner.loadPrivateKey("private.key")); //file sementara
            
            String orderNumber = decryptedData.substring(0, 1) + decryptedData.substring(decryptedData.length() - 2);
            String orderDetails = decryptedData.substring(1, decryptedData.length() - 2);

            model.addAttribute("orderNumber", orderNumber);
            model.addAttribute("orderDetails", orderDetails);

            Files.delete(tempFile);

        } catch(Exception e){
            e.printStackTrace();
        }
        return "/Pembeli/result";
    }
}
