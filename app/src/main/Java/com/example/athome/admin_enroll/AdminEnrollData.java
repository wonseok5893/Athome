package com.example.athome.admin_enroll;


import android.os.Bundle;

import com.example.athome.R;

public class AdminEnrollData{
    public String enrollPhnum;
    public String enrollUserId;
    public String enrollLocation;
    public String enrollPnum; //주차구역 번호
    public String enrollBrith;


    public String getEnrollBrith() {
        return enrollBrith;
    }

    public String getEnrollPhnum() {
        return enrollPhnum;
    }

    public String getEnrollUserId() {
        return enrollUserId;
    }

    public String getEnrollLocation() {
        return enrollLocation;
    }

   public String getEnrollPnum(){
        return enrollPnum;
   }

   public AdminEnrollData(String enrollUserId,String enrollPhnum,String enrollLocation){
        this.enrollLocation=enrollLocation;
        this.enrollUserId=enrollUserId;
        this.enrollPhnum = enrollPhnum;
   }

}
