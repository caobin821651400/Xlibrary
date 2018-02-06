package com.cb.xlibrary.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by caobin on 2018/1/31.
 */

public class DesUtils {

    public DesUtils() {
    }

    public static byte[] encryptDES5(byte[] src, String encryptKey) throws Exception {
        byte[] iv = new byte[8];
        byte[] keyBytes = encryptKey.getBytes("UTF-8");

        int i;
        for (i = 0; i < keyBytes.length && i < 8; ++i) {
            iv[i] = keyBytes[i];
        }

        if (keyBytes.length < 8) {
            for (i = keyBytes.length; i < 8; ++i) {
                iv[i] = 0;
            }
        }

        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(iv, "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(1, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(src);
        return encryptedData;
    }

    public static byte[] encryptDES7(byte[] src, String encryptKey) throws Exception {
        byte[] iv = new byte[8];
        byte[] keyBytes = encryptKey.getBytes("UTF-8");

        int i;
        for (i = 0; i < keyBytes.length && i < 8; ++i) {
            iv[i] = keyBytes[i];
        }

        if (keyBytes.length < 8) {
            for (i = keyBytes.length; i < 8; ++i) {
                iv[i] = 0;
            }
        }

        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(iv, "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS7Padding");
        cipher.init(1, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(src);
        return encryptedData;
    }

    public static String decryptDES(byte[] dest, String decryptKey) throws Exception {
        byte[] iv = new byte[8];
        byte[] keyBytes = decryptKey.getBytes("UTF-8");

        int i;
        for (i = 0; i < keyBytes.length && i < 8; ++i) {
            iv[i] = keyBytes[i];
        }

        if (keyBytes.length < 8) {
            for (i = keyBytes.length; i < 8; ++i) {
                iv[i] = 0;
            }
        }

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(iv, "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(2, key, ivSpec);
        byte[] decryptedData = cipher.doFinal(dest);
        return new String(decryptedData);
    }
}
