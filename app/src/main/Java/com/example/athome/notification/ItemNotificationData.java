package com.example.athome.notification;

public class ItemNotificationData {
    public String notificationTitle;
    public String notificationContext;
    public String notificationDate;
    public String notificationTime;

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationContext() {
        return notificationContext;
    }

    public void setNotificationContext(String notificationContext) {
        this.notificationContext = notificationContext;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public ItemNotificationData(String notificationTitle, String notificationContext, String notificationDate, String notificationTime){
        this.notificationTitle=notificationTitle;
        this.notificationContext=notificationContext;
        this.notificationDate=notificationDate;
        this.notificationTime=notificationTime;
    }
}
