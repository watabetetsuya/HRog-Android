package jp.co.goalist.hrog.hrog.user.service;

/**
 * Created by RicoShiota on 2015/12/23.
 */
public interface UserService {

//    static final String BASE_URL = "http://hrogserver.net/hrog/rest";
//
//    static final String PROJECT_NUMBER = "111774585185";

    static final String CREATE_URL = "/user/create";

    static final String INFO_URL = "/AuthNeeded/userinfo";

    //テスト環境
    static final String BASE_URL = "https://dev01.hrogserver.net/hrogtest/rest";
//   static final String BASE_URL = "http://192.168.2.167:8080/hrog-deviceid-server/rest";

    static final String PROJECT_NUMBER = "293218857999";
}
