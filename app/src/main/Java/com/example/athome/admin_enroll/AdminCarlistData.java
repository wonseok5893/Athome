package com.example.athome.admin_enroll;

public class AdminCarlistData {
    private String carlistId;
    private String carlistCarnum;
    private String carlistName;

    public AdminCarlistData(String carlistId, String carlistCarnum, String carlistName){
        this.carlistId=carlistId;
        this.carlistCarnum=carlistCarnum;
        this.carlistName=carlistName;
    }

    public String getCarlistName() {
        return carlistName;
    }

    public String getCarlistCarnum() {
        return carlistCarnum;
    }

    public String getCarlistId() {
        return carlistId;
    }
}
