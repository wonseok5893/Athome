package com.example.athome.reservation_list;

public class ItemPastReservData {
    public String pastReservStartDate;
    public String pastReservEndDate;
    public String pastReservStartTime;
    public String pastReservEndTime;
    public String pastReservCarNumber;
    public String pastReservParkingNumber;
    public String pastReservParkingAddress;

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

    public String getPastReservParkingNumber() {
        return pastReservParkingNumber;
    }

    public void setPastReservParkingNumber(String pastReservParkingNumber) {
        this.pastReservParkingNumber = pastReservParkingNumber;
    }

    public String getPastReservParkingAddress() {
        return pastReservParkingAddress;
    }

    public void setPastReservParkingAddress(String pastReservParkingAddress) {
        this.pastReservParkingAddress = pastReservParkingAddress;
    }

    public ItemPastReservData(String pastReservStartDate,
                              String pastReservEndDate,
                              String pastReservStartTime,
                              String pastReservEndTime,
                              String pastReservCarNumber,
                              String pastReservParkingNumber,
                              String pastReservParkingAddress) {
        this.pastReservEndDate=pastReservEndDate;
        this.pastReservStartTime=pastReservStartTime;
        this.pastReservEndTime=pastReservEndTime;
        this.pastReservCarNumber=pastReservCarNumber;
        this.pastReservParkingNumber=pastReservParkingNumber;
        this.pastReservParkingAddress=pastReservParkingAddress;

    }

}