package jp.co.goalist.hrog.hrog.user.service;


import java.security.MessageDigest;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jp.co.goalist.hrog.hrog.user.util.Base64Util;

public class CryptService {
    private static Logger log = Logger.getLogger(CryptService.class.getName());

    public String createMd5Digest(String src) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(src.getBytes());

            StringBuilder bd = new StringBuilder();
            for (byte b : hash) {
                bd.append(String.format("%02x", b & 0xff));
            }
            return bd.toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String encryptWithAES(String src) {
        byte[] data = src.getBytes();
        try {
            SecretKeySpec sksSpec = new SecretKeySpec("p8fY5Kp1pcf7kVpw".getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            IvParameterSpec ips = new IvParameterSpec("SdArRt5iS8Cbht9i".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, sksSpec, ips);

            byte[] encrypted = cipher.doFinal(data);
            System.out.println(encrypted.length);

            String s = new String(Base64Util.encodeBase64AndEscape(encrypted));
            System.out.println(s);
            return  s;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
