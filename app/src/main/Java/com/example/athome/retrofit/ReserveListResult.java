package com.example.athome.retrofit;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReserveListResult {

    @SerializedName("reservationList")
    @Expose
    private List<ReservationList> reservationList = null;

    public List<ReservationList> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<ReservationList> reservationList) {
        this.reservationList = reservationList;
    }

    public class ReservationList {

        @SerializedName("startTime")
        @Expose
        private String startTime;
        @SerializedName("endTime")
        @Expose
        private String endTime;

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

    }

}