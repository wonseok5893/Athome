package com.example.athome.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class nonUserReserveData {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("carNumber")
    @Expose
    private String carNumber;
    @SerializedName("notUserPhoneNumber")
    @Expose
    private String notUserPhoneNumber;
    @SerializedName("notUserName")
    @Expose
    private String notUserName;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("sum")
    @Expose
    private Integer sum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getNotUserPhoneNumber() {
        return notUserPhoneNumber;
    }

    public void setNotUserPhoneNumber(String notUserPhoneNumber) {
        this.notUserPhoneNumber = notUserPhoneNumber;
    }

    public String getNotUserName() {
        return notUserName;
    }

    public void setNotUserName(String notUserName) {
        this.notUserName = notUserName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

}
