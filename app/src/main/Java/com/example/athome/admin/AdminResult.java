package com.example.athome.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminResult {
    @SerializedName("editPasswordResult")
    @Expose
    private String editPasswordRes;
    @SerializedName("editPhoneResult")
    @Expose
    private String editPhoneRes;
    @SerializedName("editPointResult")
    @Expose
    private String editPointRes;
    @SerializedName("editStateResult")
    @Expose
    private String editStateRes;
    @SerializedName("registerLocationResult")
    @Expose
    private String registerLocationRes;

    public String getEditPasswordRes() {
        return editPasswordRes;
    }

    public String getEditPhoneRes() {
        return editPhoneRes;
    }

    public String getRegisterLocationRes() {
        return registerLocationRes;
    }

    public String getEditPointRes() {
        return editPointRes;
    }

    public String getEditStateRes() {
        return editStateRes;
    }
}