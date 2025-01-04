package com.example;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.crypto.Cipher;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.imageio.ImageIO;

public class QRCodeScanner {

    public static String readQRCode(String filePath) throws Exception {
        Path path = new File(filePath).toPath(); //ambil path QR Code
        BufferedImage image = ImageIO.read(path.toFile()); //Baca gambar QR COde
        LuminanceSource source = new BufferedImageLuminanceSource(image); //Scan QR Code
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source)); //Buat bitMap

        Result result = new MultiFormatReader().decode(bitmap); //Baca data dari bitmap
        return result.getText();
    }

    public static String decrypt(String encryptedData, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA"); //CIpher gunakan algoritma RSA
        cipher.init(Cipher.DECRYPT_MODE, privateKey); //Decrypt data
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData)); //Hasil enkripsi ke array byte
        return new String(decryptedData); //array byte to string
    }

    public static PrivateKey loadPrivateKey(String filename) throws Exception{
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename)); //Baca file private.key
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes); //Mengubah format ke PKCS#8
        KeyFactory kf = KeyFactory.getInstance("RSA"); 
        return kf.generatePrivate(spec); //Menghasilkan privateKey dari file

    }
}
