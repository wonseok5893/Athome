package com.example.athome.account;

public class ItemAccountCarData {
    private String carNumber;

    private ItemAccountCarData(){}

    public ItemAccountCarData(String carNumber) {
        this.carNumber=carNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
