package com.example.athome.admin;

public class AdminUserClickedReservData {

    private String carnum;
    private String loc;
    private String resv_date;
    private String resv_start;
    private String resv_end;
    private Integer state;

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getResv_date() {
        return resv_date;
    }

    public void setResv_date(String resv_date) {
        this.resv_date = resv_date;
    }

    public String getResv_start() {
        return resv_start;
    }

    public void setResv_start(String resv_start) {
        this.resv_start = resv_start;
    }

    public String getResv_end() {
        return resv_end;
    }

    public void setResv_end(String resv_end) {
        this.resv_end = resv_end;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
