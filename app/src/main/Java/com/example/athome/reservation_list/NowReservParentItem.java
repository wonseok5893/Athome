package com.example.athome.reservation_list;

import java.util.ArrayList;

public class NowReservParentItem {
    private String groupState;
    private String groupParking;
    private String groupDate;
    public ArrayList<NowReservChildItem> child;

    public String getGroupState(){
        return groupState;
    }

    public String getGroupParking(){
        return groupParking;
    }

    public String getGroupDate(){
        return groupDate;
    }

    public NowReservParentItem(String groupState,String groupParking, String groupDate){
        this.groupState=groupState;
        this.groupParking=groupParking;
        this.groupDate=groupDate;
        child=new ArrayList<>();
    }
}
