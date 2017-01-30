package jp.co.goalist.hrog.hrog.user.model;

public class AuthTokenModel {
    protected String userId;
    protected String password;

    public AuthTokenModel() {
    }

    public AuthTokenModel(String password, String userId) {
        this.password = password;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
