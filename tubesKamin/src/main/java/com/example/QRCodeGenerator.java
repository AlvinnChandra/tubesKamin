package com.example;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.time.Instant;

@Component
public class QRCodeGenerator {
    
    //Bikin String Buat di encrypt
    public String generateQRCodeString(String orderNumber, String orderDetails){
        String firstDigit = orderNumber.substring(0, 1);
        String lastTwoDigit = orderNumber.substring(orderNumber.length() - 2);
        String formattedDetails = orderDetails.replaceAll("\\s", ""); //Hapus Spasi
        return firstDigit + formattedDetails + lastTwoDigit;
    }

    //Membuat public key dan private key
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); //Membuat instance key generator yang menggunakan algoritma RSA
        keyGen.initialize(2048); //Besar ukuran kunci
        return keyGen.generateKeyPair(); //Menghasilkan public key dan private key
    }

    public String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA"); //Object Cipher untuk enkripsi dengan algoritma RSA
        cipher.init(Cipher.ENCRYPT_MODE, publicKey); //Encrypt menggunakan public Key
        byte[] encryptedData = cipher.doFinal(data.getBytes()); //Konversi String menjadi byte agar dapat dienkripsi
        return Base64.getEncoder().encodeToString(encryptedData); //mengubah kembali ke string
    }

    //Waktu expire QR
    public static Date getExpiryTime(int minutes){
        return Date.from(Instant.now().plusSeconds(minutes * 60));
    }

    public static void generateQRCode(String data, String filePath) throws Exception {
        Path path = Paths.get(filePath); //Path qr code disimpan
        if(!Files.exists(path.getParent())){
            Files.createDirectories(path.getParent()); //Cek file
        }

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200); //membuat QR Code
        MatrixToImageWriter.writeToPath(matrix, "PNG", path); //BitMatrix diubah menjadi png
    }

    public static void saveKeyToFile(String filename, Key key) throws Exception {
        try(FileOutputStream fos = new FileOutputStream(filename)){
            fos.write(key.getEncoded());
        }
    }

    public PublicKey loadPublicKey(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(spec);
    }
}