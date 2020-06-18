package com.example.athome.shared_time;


public class ItemMyNowListData {
    public String nowReservStartDate;
    public String nowReservEndDate;
    public String nowReservStartTime;
    public String nowReservEndTime;
    public String nowReservCarNumber;
    public String nowReservPhoneNumber;
    public String nowReservState;



    public ItemMyNowListData(String nowReservStartDate, String nowReservEndDate, String nowReservStartTime, String nowReservEndTime, String nowReservCarNumber, String nowReservPhoneNumber, String nowReservState) {
        this.nowReservStartDate = nowReservStartDate;
        this.nowReservEndDate = nowReservEndDate;
        this.nowReservStartTime = nowReservStartTime;
        this.nowReservEndTime = nowReservEndTime;
        this.nowReservCarNumber = nowReservCarNumber;
        this.nowReservPhoneNumber = nowReservPhoneNumber;
        this.nowReservState = nowReservState;
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

    public String getNowReservPhoneNumber() {
        return nowReservPhoneNumber;
    }

    public void setNowReservPhoneNumber(String nowReservPhoneNumber) {
        this.nowReservPhoneNumber = nowReservPhoneNumber;
    }

    public String getNowReservState() {
        return nowReservState;
    }

    public void setNowReservState(String nowReservState) {
        this.nowReservState = nowReservState;
    }
}
