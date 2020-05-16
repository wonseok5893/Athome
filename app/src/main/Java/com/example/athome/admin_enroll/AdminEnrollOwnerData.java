package com.example.athome.admin_enroll;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminEnrollOwnerData implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;

    protected AdminEnrollOwnerData(Parcel in) {
        id = in.readString();
        userId = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userPhone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(userPhone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminEnrollOwnerData> CREATOR = new Creator<AdminEnrollOwnerData>() {
        @Override
        public AdminEnrollOwnerData createFromParcel(Parcel in) {
            return new AdminEnrollOwnerData(in);
        }

        @Override
        public AdminEnrollOwnerData[] newArray(int size) {
            return new AdminEnrollOwnerData[size];
        }
    };



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

}

