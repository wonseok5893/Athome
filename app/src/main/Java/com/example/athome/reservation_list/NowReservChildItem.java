package com.example.athome.reservation_list;


public class NowReservChildItem {
    private String childParking;
    private String childPlace;
    private String childPaydate;
    private String childDate;
    private String childTime;
    private String childCar;
    private String childState;


    public String getChildParking(){
        return childParking;
    }

    public String getChildPlace(){
        return childPlace;}


    public String getChildPaydate(){
        return childPaydate;
    }
    public String getChildDate(){
        return childDate;
    }
    public String getChildTime(){
        return childTime;
    }
    public String getChildCar(){
        return childCar;
    }
    public String getChildState(){
        return childState;
    }

    public NowReservChildItem(String childParking, String childPlace, String childPaydate, String childDate, String childTime, String childCar, String childState){
        this.childParking=childParking;
        this.childPlace=childPlace;
        this.childPaydate=childPaydate;
        this.childDate=childDate;
        this.childTime=childTime;
        this.childCar=childCar;
        this.childState=childState;
    }



}
