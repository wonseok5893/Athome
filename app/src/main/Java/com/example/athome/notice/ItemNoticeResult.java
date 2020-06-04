package com.example.athome.notice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemNoticeResult {
    @SerializedName("data")
    @Expose
    private List<ItemNoticeData> data = null;

    public List<ItemNoticeData> getData() {
        return data;
    }

    public void setData(List<ItemNoticeData> data) {
        this.data = data;
    }
}
