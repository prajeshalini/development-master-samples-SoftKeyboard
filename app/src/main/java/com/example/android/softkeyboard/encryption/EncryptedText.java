package com.example.android.softkeyboard.encryption;

/**
 * Created by Shalini Prajesh on 19/3/18.
 */

public class EncryptedText {
    String messageText;
    String secretKey;

    public EncryptedText(String messageText, String secretKey) {
        this.messageText = messageText;
        this.secretKey = secretKey;
    }
}
