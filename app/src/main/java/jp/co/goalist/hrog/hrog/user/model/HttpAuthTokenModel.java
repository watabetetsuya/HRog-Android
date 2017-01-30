package jp.co.goalist.hrog.hrog.user.model;

public class HttpAuthTokenModel extends AuthTokenModel {
    protected String salt;

    public HttpAuthTokenModel() {
    }

    public HttpAuthTokenModel(String uid, String pwd, String salt) {
        this.userId = uid;
        this.password = pwd;
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
