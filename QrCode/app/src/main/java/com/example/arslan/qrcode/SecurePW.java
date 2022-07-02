package com.example.arslan.qrcode;


import android.content.Context;
import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurePW
{
    Context context;
    public SecurePW(Context context)
    {
        this.context=context;
    }

    String AES = "AES";

    public String encrypt (String pw) throws Exception
    {
        SecretKeySpec key = generateKey(pw);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(pw.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String pw) throws Exception
    {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes =pw.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    public String decrypt (String pw) throws Exception
    {
        SecretKeySpec key = generateKey(pw);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte [] decodedValue = Base64.decode(pw, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String (decValue);
        return decryptedValue;
    }
}
