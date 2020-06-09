package com.example.athome.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReservationListResult implements Parcelable {
    @SerializedName("data")
    @Expose
    private List<ReservationListResult_data> data = null;

    protected ReservationListResult(Parcel in) {
    }

    public static final Creator<ReservationListResult> CREATOR = new Creator<ReservationListResult>() {
        @Override
        public ReservationListResult createFromParcel(Parcel in) {
            return new ReservationListResult(in);
        }

        @Override
        public ReservationListResult[] newArray(int size) {
            return new ReservationListResult[size];
        }
    };

    public List<ReservationListResult_data> getData() {
        return data;
    }

    public void setData(List<ReservationListResult_data> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }



}
