package com.example.athome.admin_notice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ItemAdminNoticeData {
    @SerializedName("title")
    @Expose
    public String noticeTitle;
    @SerializedName("enrollTime")
    @Expose
    public String noticeDate;
    @SerializedName("description")
    @Expose
    public String noticeContext;


    public String getNoticeTitle(){
        return noticeTitle;
    }

    public String getNoticeDate(){
        return noticeDate;
    }

    public String getNoticeContext(){
        return noticeContext;
    }

    public ItemAdminNoticeData(String noticeTitle, String noticeDate, String noticeContext){
        this.noticeTitle=noticeTitle;
        this.noticeDate=noticeDate;
        this.noticeContext=noticeContext;
    }

}

