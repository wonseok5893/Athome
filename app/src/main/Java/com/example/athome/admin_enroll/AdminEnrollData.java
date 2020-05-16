package com.example.athome.admin_enroll;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.athome.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.security.acl.Owner;
import java.util.Date;

public class AdminEnrollData implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private AdminEnrollOwnerData owner;
    @SerializedName("filePath")
    @Expose
    private String filePath;
    @SerializedName("userCarNumber")
    @Expose
    private String userCarNumber;
    @SerializedName("userBirth")
    @Expose
    private String userBirth;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("parkingInfo")
    @Expose
    private String parkingInfo;
    @SerializedName("enrollTime")
    @Expose
    private Date enrollTime;
    @SerializedName("state")
    @Expose
    private Integer state;

    protected AdminEnrollData(Parcel in) {
        id = in.readString();
        filePath = in.readString();
        userCarNumber = in.readString();
        userBirth = in.readString();
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        parkingInfo = in.readString();
    }

    public static final Creator<AdminEnrollData> CREATOR = new Creator<AdminEnrollData>() {
        @Override
        public AdminEnrollData createFromParcel(Parcel in) {
            return new AdminEnrollData(in);
        }

        @Override
        public AdminEnrollData[] newArray(int size) {
            return new AdminEnrollData[size];
        }
    };

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdminEnrollOwnerData getOwner() {
        return owner;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUserCarNumber() {
        return userCarNumber;
    }

    public void setUserCarNumber(String userCarNumber) {
        this.userCarNumber = userCarNumber;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getParkingInfo() {
        return parkingInfo;
    }

    public void setParkingInfo(String parkingInfo) {
        this.parkingInfo = parkingInfo;
    }

    public Date getEnrollTime() {
        return enrollTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(filePath);
        dest.writeString(userCarNumber);
        dest.writeString(userBirth);
        dest.writeString(location);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(parkingInfo);
    }
}
