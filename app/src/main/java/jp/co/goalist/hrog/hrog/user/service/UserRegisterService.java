package jp.co.goalist.hrog.hrog.user.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.co.goalist.hrog.hrog.user.model.AuthTokenModel;

public class UserRegisterService implements UserService{
    private static Logger log = Logger.getLogger(UserRegisterService.class.getName());

    public AuthTokenModel register(String deviceId) {
        Date baseDate = new Date();
        UserTokenService tokenSvc = new UserTokenService();
        String uid = tokenSvc.createUserIdToken(deviceId, baseDate);
        String pwd = tokenSvc.createPasswordToken(deviceId, baseDate);

        String _url = BASE_URL + CREATE_URL;

        try {
            CryptService c = new CryptService();
            URL url = new URL(_url);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String params ="msg1=" + uid + "&" +
                    "msg2=" + deviceId + "&" +
                    "msg3=" + pwd + "&" +
                    "msg4=" + "android"+ "&" +
                    "msg5=" + "false";

            PrintStream ps = new PrintStream(conn.getOutputStream());
            try  {
                ps.print(params);
            } finally {
                ps.close();
            }

            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream input = conn.getInputStream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    int i;
                    while ((i = input.read()) >= 0) {
                        output.write(i);
                    }
                    String s = new String(output.toByteArray(), "utf-8");
                    System.out.printf("ユーザー登録結果:" + s);

                } finally {
                    input.close();
                }
                log.log(Level.INFO, "ユーザー登録に成功しました");
                return new AuthTokenModel(uid,pwd);
            } else {
                throw new IllegalStateException("ユーザー登録に失敗しました：" + resCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            uid = null;
            pwd = null;
        }
        return null;
    }

}
