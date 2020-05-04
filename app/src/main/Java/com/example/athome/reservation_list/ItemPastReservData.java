package com.example.athome.reservation_list;

public class ItemPastReservData {
    public String pastReservDate;
    public String pastReservPlace;
    public String pastReservTime;
    public String pastReservPrice;

    public String getPastReservDate() {
        return pastReservDate;
    }

    public String getPastReservPlace() {
        return pastReservPlace;
    }

    public String getPastReservTime() {
        return pastReservTime;
    }

    public String getPastReservPrice() {
        return pastReservPrice;
    }

    public ItemPastReservData(String pastReservDate, String pastReservPlace, String pastReservTime, String pastReservPrice) {
        this.pastReservDate = pastReservDate;
        this.pastReservPlace = pastReservPlace;
        this.pastReservTime = pastReservTime;
        this.pastReservPrice = pastReservPrice;
    }

}