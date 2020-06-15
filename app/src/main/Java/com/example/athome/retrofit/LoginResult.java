package com.example.athome.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("result")
    @Expose
    String loginResult;
    @SerializedName("token")
    @Expose
    String token;
    @SerializedName("message")
    @Expose
    String message;


    public String getToken() {
        return token;
    }

    public String getLoginResult() {
        return loginResult;
    }
    public String getMessage() {
        return message;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }


}
