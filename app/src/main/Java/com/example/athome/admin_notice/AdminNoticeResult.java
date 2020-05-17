package com.example.athome.admin_notice;

import com.example.athome.admin_enroll.AdminEnrollData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminNoticeResult {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<ItemAdminNoticeData> data = null;

    public String getResult() {
        return result;
    }

    public List<ItemAdminNoticeData> getData() {
        return data;
    }
}
