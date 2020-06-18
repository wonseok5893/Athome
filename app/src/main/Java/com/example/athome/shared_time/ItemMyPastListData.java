package com.example.athome.shared_time;

public class ItemMyPastListData {
    public String pastReservStartDate;
    public String pastReservEndDate;
    public String pastReservStartTime;
    public String pastReservEndTime;
    public String pastReservCarNumber;
    public String pastReservPhoneNumber;


    public String getPastReservStartDate() {
        return pastReservStartDate;
    }

    public void setPastReservStartDate(String pastReservStartDate) {
        this.pastReservStartDate = pastReservStartDate;
    }

    public String getPastReservEndDate() {
        return pastReservEndDate;
    }

    public void setPastReservEndDate(String pastReservEndDate) {
        this.pastReservEndDate = pastReservEndDate;
    }

    public String getPastReservStartTime() {
        return pastReservStartTime;
    }

    public void setPastReservStartTime(String pastReservStartTime) {
        this.pastReservStartTime = pastReservStartTime;
    }

    public String getPastReservEndTime() {
        return pastReservEndTime;
    }

    public void setPastReservEndTime(String pastReservEndTime) {
        this.pastReservEndTime = pastReservEndTime;
    }

    public String getPastReservCarNumber() {
        return pastReservCarNumber;
    }

    public void setPastReservCarNumber(String pastReservCarNumber) {
        this.pastReservCarNumber = pastReservCarNumber;
    }

    public String getPastReservPhoneNumber() {
        return pastReservPhoneNumber;
    }

    public void setPastReservPhoneNumber(String pastReservPhoneNumber) {
        this.pastReservPhoneNumber = pastReservPhoneNumber;
    }


    public ItemMyPastListData(String pastReservStartDate,
                              String pastReservEndDate,
                              String pastReservStartTime,
                              String pastReservEndTime,
                              String pastReservCarNumber,
                              String pastReservPhoneNumber
                             ) {
        this.pastReservStartDate = pastReservStartDate;
        this.pastReservEndDate = pastReservEndDate;
        this.pastReservStartTime = pastReservStartTime;
        this.pastReservEndTime = pastReservEndTime;
        this.pastReservCarNumber = pastReservCarNumber;
        this.pastReservPhoneNumber = pastReservPhoneNumber;


    }

}