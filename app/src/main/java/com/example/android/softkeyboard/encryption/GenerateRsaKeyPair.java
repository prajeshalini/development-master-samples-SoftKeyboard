package com.example.android.softkeyboard.encryption;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class GenerateRsaKeyPair {
  private static GenerateRsaKeyPair generateRsaKeyPair;
  public String privateKey;
  public String publicKey;
  public byte[] secretKey;

  private GenerateRsaKeyPair(){
  }

  public static GenerateRsaKeyPair getInstance(){
    if(generateRsaKeyPair == null){
      generateRsaKeyPair = new GenerateRsaKeyPair();
    }
    return generateRsaKeyPair;
  }
  public void generateKeys() {
    try {

      //  generate public key and private key
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(1024); // key length
      KeyPair keyPair = keyPairGenerator.genKeyPair();
      String privateKeyString = Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.DEFAULT);
      String publicKeyString = Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT);


      // generate secret key using AES
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128); // AES is currently available in three key sizes: 128, 192 and 256 bits.The design and strength of all key lengths of the AES algorithm are sufficient to protect classified information up to the SECRET level
      SecretKey secKey = keyGenerator.generateKey();

      secretKey = secKey.getEncoded();
      privateKey = privateKeyString;
      publicKey = publicKeyString;
//      System.out.println("rsa key pair generated\n");
//      System.out.println("privateKey\n" + privateKeyString + "\n");
//      System.out.println("publicKey\n" + publicKeyString + "\n\n");

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}