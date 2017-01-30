package jp.co.goalist.hrog.hrog.user.util;

import java.io.UnsupportedEncodingException;

import jp.co.goalist.hrog.hrog.imported.ImportedBase64;

public class Base64Util {

    public static String encodeBase64AndEscape(byte[] data) {
        String s = null;
        try {
            s = new String(ImportedBase64.encode(data), "UTF-8");

            //パラメータが%でエスケープされることを防ぐ。
            s = s.replace("+", "_").replace("/", "-").replace("=", ".");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
