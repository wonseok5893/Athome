package com.example.athome.retrofit;

import com.google.gson.annotations.SerializedName;

public class AuthResult {
    //User에 담을 정보들
    @SerializedName("userId")
    String userId;
    @SerializedName("userEmail")
    String userEmail;
    @SerializedName("userPhone")
    String userPhone;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


}
