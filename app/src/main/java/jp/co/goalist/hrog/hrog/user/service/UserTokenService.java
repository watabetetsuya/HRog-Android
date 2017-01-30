package jp.co.goalist.hrog.hrog.user.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.co.goalist.hrog.hrog.user.model.AuthTokenModel;
import jp.co.goalist.hrog.hrog.user.model.HttpAuthTokenModel;

public class UserTokenService {

    private String fromDate(Date date, String format ) {
        String s = new SimpleDateFormat(format).format(date);
        s = Long.toHexString(Long.parseLong(s));
        return s;
    }

    private String shuffle(String s1) {
        List<String> buf = new ArrayList<>();
        for (String s : s1.split("")) {
            buf.add(s);
        }
        Collections.shuffle(buf);
        StringBuilder b = new StringBuilder();
        for (String s : buf) {
            b.append(s);
        }
        return b.toString();
    }

    public String createUserIdToken(String deviceId, Date baseDate) {
        String s1 = fromDate(baseDate, "HHddssyymmms");
        String s2 = fromDate(baseDate, "MMddmmssHH");
        return (s1 + shuffle(deviceId + s2)).toUpperCase();
    }

    public String createPasswordToken(String password, Date baseDate) {
        String s1 = fromDate(baseDate, "ddMMssmmMM");
        String s2 = fromDate(baseDate, "MMmmddssHHyyddssmm");
        return (shuffle(s2 + password) + s1).toUpperCase();
    }

    public String createSalt(Date baseDate) {
        return shuffle(fromDate(baseDate, "MMmmddssHHddMMHHyy")).toUpperCase();
    }

    public HttpAuthTokenModel createAuthToken(AuthTokenModel model) {
        Date baseDate = new Date();
        String salt = createSalt(baseDate);
        String pwd = (new CryptService().createMd5Digest(model.getPassword() + "(-_-)[" + salt))
                        .toLowerCase();
        return new HttpAuthTokenModel(model.getUserId(), pwd, salt);
    }

    public AuthTokenModel getSavedToken (Context ctx){
        SharedPreferences p = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE);
        String id = p.getString("uid",null);
        String pw = p.getString("password",null);
        if(id==null){
            return null;
        }else {
            return new AuthTokenModel(id, pw);
        }
    }
}
