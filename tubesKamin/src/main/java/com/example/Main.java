package com.example;

import java.security.*;
import javax.crypto.Cipher;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        try {
            String orderNumber = "102";
            String orderDetails = "Ayam Goreng";
            String qrCodeString = generateQRCodeString(orderNumber, orderDetails);

            KeyPair keyPair = generateKeyPair();
            String encryptedQRCode = encrypt(qrCodeString, keyPair.getPublic());

            Date expiryTime = getExpiryTime(1);

            String filePath = "src/main/resources/static/QRCode/QR102.png";
            generateQRCode(encryptedQRCode, filePath);

            System.out.println("QR Code generated at: " + filePath);
            System.out.println("Expiry time " + expiryTime);
        } catch (Exception e){
            e.printStackTrace();
        }
    } 
    
    //Bikin String Buat di encrypt
    public static String generateQRCodeString(String orderNumber, String orderDetails){
        String firstDigit = orderNumber.substring(0, 1);
        String lastTwoDigit = orderNumber.substring(orderNumber.length() - 2);
        String formattedDetails = orderDetails.replaceAll("\\s", "");
        return firstDigit + formattedDetails + lastTwoDigit;
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static Date getExpiryTime(int minutes){
        return Date.from(Instant.now().plusSeconds(minutes * 60));
    }

    public static void generateQRCode(String data, String filePath) throws Exception {
        Path path = Paths.get(filePath);
        if(!Files.exists(path.getParent())){
            Files.createDirectories(path.getParent());
        }

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);
    }
}
