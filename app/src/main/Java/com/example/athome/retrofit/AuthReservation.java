package com.example.athome.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthReservation {
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("sum")
    @Expose
    private Integer sum;
    @SerializedName("state")
    @Expose
    private Integer state;


}
