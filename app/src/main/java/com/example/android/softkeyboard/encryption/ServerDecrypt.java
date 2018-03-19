package com.example.android.softkeyboard.encryption;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ServerDecrypt {

    private String privateKey = GenerateRsaKeyPair.getInstance().privateKey;
    private String encryptedTextString = "<your_received_encrypted_text_here>";
    private String encryptedSecretKeyString = "<your_received_encrypted_secret_key_here>";

    public ServerDecrypt(EncryptedText encryptedText) {
        this.encryptedTextString = encryptedText.messageText;
        this.encryptedSecretKeyString = encryptedText.secretKey;
    }

    public String decrypt() {
        String text = null;
        try {

            //  Get private key
            PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey, Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(privateSpec);

            // Decrypt encrypted secret key using private key
            Cipher cipher1 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher1.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] secretKeyBytes = cipher1.doFinal(Base64.decode(encryptedSecretKeyString, Base64.DEFAULT));
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, "AES");

            // Decrypt encrypted text using secret key
            byte[] raw = secretKey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            byte[] original = cipher.doFinal(Base64.decode(encryptedTextString, Base64.DEFAULT));
            text = new String(original, Charset.forName("UTF-8"));

            // Print the original text sent by client
            // System.out.println("text decrypted\n" + text + "\n\n");

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return text;
    }
}