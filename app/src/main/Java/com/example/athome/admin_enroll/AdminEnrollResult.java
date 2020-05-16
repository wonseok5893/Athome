package com.example.athome.admin_enroll;

import com.example.athome.admin.AllUserData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminEnrollResult {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<AdminEnrollData> data = null;

    public String getResult() {
        return result;
    }

    public List<AdminEnrollData> getData() {
        return data;
    }
}
