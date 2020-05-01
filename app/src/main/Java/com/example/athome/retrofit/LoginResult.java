package com.example.athome.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("result")
    String loginResult;
    @SerializedName("token")
    String token;


    public String getToken() {
        return token;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }


}
