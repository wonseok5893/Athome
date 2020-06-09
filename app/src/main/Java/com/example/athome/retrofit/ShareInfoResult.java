package com.example.athome.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShareInfoResult {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("Sun")
    @Expose
    private Integer sun;
    @SerializedName("Mon")
    @Expose
    private Integer mon;
    @SerializedName("Tue")
    @Expose
    private Integer tue;
    @SerializedName("Wed")
    @Expose
    private Integer wed;
    @SerializedName("Thu")
    @Expose
    private Integer thu;
    @SerializedName("Fri")
    @Expose
    private Integer fri;
    @SerializedName("Sat")
    @Expose
    private Integer sat;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Integer getSun() {
        return sun;
    }

    public void setSun(Integer sun) {
        this.sun = sun;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public Integer getTue() {
        return tue;
    }

    public void setTue(Integer tue) {
        this.tue = tue;
    }

    public Integer getWed() {
        return wed;
    }

    public void setWed(Integer wed) {
        this.wed = wed;
    }

    public Integer getThu() {
        return thu;
    }

    public void setThu(Integer thu) {
        this.thu = thu;
    }

    public Integer getFri() {
        return fri;
    }

    public void setFri(Integer fri) {
        this.fri = fri;
    }

    public Integer getSat() {
        return sat;
    }

    public void setSat(Integer sat) {
        this.sat = sat;
    }


}
