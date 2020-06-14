package com.example.athome.retrofit;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResult {

    //User에 담을 정보들
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("user")
    @Expose
    private AuthUser authUser;
    @SerializedName("message")
    @Expose
    private String authMessage;
    @SerializedName("todaySharingState")
    @Expose
    private Integer todaySharingState;

    public Integer getTodaySharingState() {
        return todaySharingState;
    }

    public void setTodaySharingState(Integer todaySharingState) {
        this.todaySharingState = todaySharingState;
    }


    public String getAuthMessage() {
        return authMessage;
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }


}