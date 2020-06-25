package com.example.athome.reservation_list;


public class ItemNowReservData {
    public String nowReservStartDate;
    public String nowReservEndDate;
    public String nowReservStartTime;
    public String nowReservEndTime;
    public String nowReservCarNumber;
    public String nowReservParkingNumber;
    public String nowReservState;
    public String nowReservLocation;
    // public String nowReservParkingAddress;


    public ItemNowReservData(String nowReservStartDate, String nowReservEndDate, String nowReservStartTime, String nowReservEndTime, String nowReservCarNumber, String nowReservParkingNumber, String nowReservState, String nowReservLocation) {
        this.nowReservStartDate = nowReservStartDate;
        this.nowReservEndDate = nowReservEndDate;
        this.nowReservStartTime = nowReservStartTime;
        this.nowReservEndTime = nowReservEndTime;
        this.nowReservCarNumber = nowReservCarNumber;
        this.nowReservParkingNumber = nowReservParkingNumber;
        this.nowReservState = nowReservState;
        this.nowReservLocation =nowReservLocation;
    }

    public String getNowReservStartDate() {
        return nowReservStartDate;
    }

    public void setNowReservStartDate(String nowReservStartDate) {
        this.nowReservStartDate = nowReservStartDate;
    }

    public String getNowReservEndDate() {
        return nowReservEndDate;
    }

    public void setNowReservEndDate(String nowReservEndDate) {
        this.nowReservEndDate = nowReservEndDate;
    }

    public String getNowReservStartTime() {
        return nowReservStartTime;
    }

    public void setNowReservStartTime(String nowReservStartTime) {
        this.nowReservStartTime = nowReservStartTime;
    }

    public String getNowReservEndTime() {
        return nowReservEndTime;
    }

    public void setNowReservEndTime(String nowReservEndTime) {
        this.nowReservEndTime = nowReservEndTime;
    }

    public String getNowReservCarNumber() {
        return nowReservCarNumber;
    }

    public void setNowReservCarNumber(String nowReservCarNumber) {
        this.nowReservCarNumber = nowReservCarNumber;
    }

    public String getNowReservParkingNumber() {
        return nowReservParkingNumber;
    }

    public void setNowReservParkingNumber(String nowReservParkingNumber) {
        this.nowReservParkingNumber = nowReservParkingNumber;
    }

    public String getNowReservState() {
        return nowReservState;
    }

    public void setNowReservState(String nowReservState) {
        this.nowReservState = nowReservState;
    }

    public String getNowReservLocation() {
        return nowReservLocation;
    }

    public void setNowReservLocation(String nowReservLocation) {
        this.nowReservLocation = nowReservLocation;
    }
}
