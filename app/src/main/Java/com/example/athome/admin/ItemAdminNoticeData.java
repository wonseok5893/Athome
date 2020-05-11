package com.example.athome.admin;

public class ItemAdminNoticeData {
    public String noticeTitle;
    public String noticeDate;
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

