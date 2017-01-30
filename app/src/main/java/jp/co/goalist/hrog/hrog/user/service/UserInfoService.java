package jp.co.goalist.hrog.hrog.user.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.co.goalist.hrog.hrog.user.model.AuthTokenModel;
import jp.co.goalist.hrog.hrog.user.model.HttpAuthTokenModel;
import jp.co.goalist.hrog.hrog.user.model.UserModel;

/**
 * Created by RicoShiota on 2015/12/23.
 */
public class UserInfoService implements UserService{
    private static Logger log = Logger.getLogger(UserInfoService.class.getName());

    public UserModel getUserData(AuthTokenModel model){
        String _url = BASE_URL + INFO_URL;

        try {
            URL url = new URL(_url);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            UserTokenService service = new UserTokenService();
            HttpAuthTokenModel token = service.createAuthToken(model);
            conn.setRequestProperty("headermsg1",token.getPassword());
            conn.setRequestProperty("headermsg2",token.getSalt());
            conn.setRequestProperty("headermsg3",token.getUserId());

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
                    System.out.printf("ユーザー取得結果:" + s);
                    JSONObject json = new JSONObject(s);
                    UserModel um = new UserModel();
                    um.setNotifyAtMorning(json.getString("notifyAtMorning").equals("true"));
                    um.setNotifyAtEvening(json.getString("notifyAtEvening").equals("true"));
                    um.setNotifyAtNoon(json.getString("notifyAtNoon").equals("true"));
                    um.setNotifyForNonPeriodic(json.getString("notifyForNonPeriodic").equals("true"));
                    return um;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    input.close();
                }

            } else {
                throw new IllegalStateException("ユーザー情報取得に失敗しました：" + resCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateUserData(AuthTokenModel model, UserModel userModel) {
        String _url = BASE_URL + INFO_URL;
        try {
            URL url = new URL(_url);
            HttpURLConnection conn = getConnection(url, model);
            conn.setRequestMethod("PUT");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String params =
                    "msg1=" + (userModel.isNotifyAtMorning() ? "true" : "false") + "&" +
                    "msg2=" + (userModel.isNotifyAtNoon() ? "true" : "false") + "&" +
                    "msg3=" + (userModel.isNotifyAtEvening() ? "true" : "false") + "&" +
                    "msg4=" + (userModel.isNotifyForNonPeriodic() ? "true" : "false");

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
                    System.out.printf("ユーザー更新結果:" + s);

                } finally {
                    input.close();
                }
                log.log(Level.INFO, "ユーザー更新に成功しました");
            } else {
                throw new IllegalStateException("ユーザー更新に失敗しました：" + resCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpURLConnection getConnection(URL url, AuthTokenModel model) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        UserTokenService service = new UserTokenService();
        HttpAuthTokenModel token = service.createAuthToken(model);
        conn.setRequestProperty("headermsg1", token.getPassword());
        conn.setRequestProperty("headermsg2", token.getSalt());
        conn.setRequestProperty("headermsg3", token.getUserId());
        return conn;
    }

}
