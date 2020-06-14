package com.example.athome.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationListResult_data implements Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("carNumber")
    @Expose
    private String carNumber;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("parkingInfo")
    @Expose
    private String parkingInfo;


    protected ReservationListResult_data(Parcel in) {
        id = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        carNumber = in.readString();
        location = in.readString();
        parkingInfo = in.readString();
    }

    public static final Creator<ReservationListResult_data> CREATOR = new Creator<ReservationListResult_data>() {
        @Override
        public ReservationListResult_data createFromParcel(Parcel in) {
            return new ReservationListResult_data(in);
        }

        @Override
        public ReservationListResult_data[] newArray(int size) {
            return new ReservationListResult_data[size];
        }
    };

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

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

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParkingInfo() {
        return parkingInfo;
    }

    public void setParkingInfo(String parkingInfo) {
        this.parkingInfo = parkingInfo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(carNumber);
        dest.writeString(location);
        dest.writeString(parkingInfo);
    }
}
