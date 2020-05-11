package com.example.athome.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllUserResult {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("users")
    @Expose
    private List<AllUserData> data = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<AllUserData> getData() {
        return data;
    }

    public void setData(List<AllUserData> data) {
        this.data = data;
    }
}
