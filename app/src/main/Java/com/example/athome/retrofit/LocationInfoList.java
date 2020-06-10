package com.example.athome.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationInfoList {

    @SerializedName("locationInfo")
    @Expose
    private LocationInfo locationInfo;

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }

    public class LocationInfo {

        @SerializedName("possibleStartTime")
        @Expose
        private String possibleStartTime;
        @SerializedName("possibleEndTime")
        @Expose
        private String possibleEndTime;
        @SerializedName("timeState")
        @Expose
        private List<Integer> timeState = null;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getPossibleStartTime() {
            return possibleStartTime;
        }

        public void setPossibleStartTime(String possibleStartTime) {
            this.possibleStartTime = possibleStartTime;
        }

        public String getPossibleEndTime() {
            return possibleEndTime;
        }

        public void setPossibleEndTime(String possibleEndTime) {
            this.possibleEndTime = possibleEndTime;
        }

        public List<Integer> getTimeState() {
            return timeState;
        }

        public void setTimeState(List<Integer> timeState) {
            this.timeState = timeState;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

}
