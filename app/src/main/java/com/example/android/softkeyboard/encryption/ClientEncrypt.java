package com.example.android.softkeyboard.encryption;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ClientEncrypt {

    private static String publicKeyString = GenerateRsaKeyPair.getInstance().publicKey;
    private byte[] secretKey;

    public ClientEncrypt(byte[] key) {
        this.secretKey = key;
    }

    /**
     * Encrypts string and secretKey using secret key
     *
     * @param raw The plain text to encrypt
     */
    public EncryptedText encryptMessage(byte[] raw, String text) {
        String cipherTextString = null;
        String encryptedSecretKey = null;
        try {
            EncryptedText encryptedText;
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            cipherTextString = Base64.encodeToString(cipher.doFinal(text.getBytes(Charset.forName("UTF-8"))), Base64.DEFAULT);

            // get public key
            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.decode(publicKeyString, Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicSpec);

            //encrypt secret key using public key
            Cipher cipher2 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher2.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedSecretKey = Base64.encodeToString(cipher2.doFinal(secretKey), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return new EncryptedText(cipherTextString, encryptedSecretKey);
    }

//  public static void main (String args[]) {
//    try {
//
//
//      String text = "Hello Shalini Prajesh Here";
//      System.out.println("Text to be encrpted"+text+"\n\n");
//
//      //  encrypt string using secret key
//      //byte[] raw = ;
//      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
//      String cipherTextString = Base64.encodeToString(cipher.doFinal(text.getBytes(Charset.forName("UTF-8"))), Base64.DEFAULT);
//
//      System.out.println("Encrpted Text"+cipherTextString+"\n\n");
//      // get public key
//      X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.decode(publicKeyString, Base64.DEFAULT));
//      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//      PublicKey publicKey = keyFactory.generatePublic(publicSpec);
//
//      //  encrypt secret key using public key
//      Cipher cipher2 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
//      cipher2.init(Cipher.ENCRYPT_MODE, publicKey);
//      String encryptedSecretKey = Base64.encodeToString(cipher2.doFinal(secretKey.getEncoded()), Base64.DEFAULT);
//
//      System.out.println("Encrypted Secret Key"+encryptedSecretKey);
//      //  pass cipherTextString (encypted sensitive data) and encryptedSecretKey to your server via your preferred way.
//      // Tips:
//      // You may use JSON to combine both the strings under 1 object.
//      // You may use a volley call to send this data to your server.
//      ServerDecrypt serverDecrypt = new ServerDecrypt();
//      ServerDecrypt.encryptedSecretKeyString = encryptedSecretKey;
//      ServerDecrypt.encryptedTextString = cipherTextString;
//      serverDecrypt.decrypt();
//
//    } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
//      e.printStackTrace();
//    }
//  }
}