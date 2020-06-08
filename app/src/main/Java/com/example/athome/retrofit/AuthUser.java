package com.example.athome.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthUser {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;
    @SerializedName("userCarNumber")
    @Expose
    private String userCarNumber;
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("sharingParkingLot")
    @Expose
    AuthSharedLocation authSharedLocation;
    @SerializedName("state")
    @Expose
    private Integer state;

//    @SerializedName("reservation")
//    @Expose
//    private List<AuthReservation> reservation;

    public Integer getState() {
        return state;
    }

    public AuthSharedLocation getAuthSharedLocation() {
        return authSharedLocation;
    }

    public Integer getPoint() {
        return point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserCarNumber() {
        return userCarNumber;
    }

    public void setUserCarNumber(String userCarNumber) {
        this.userCarNumber = userCarNumber;
    }


}

