package com.example.athome.admin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminUserClickedShareData {
    private String id;
    private String loc;
    private String share_date;
    private String share_start;
    private String share_end;
    private Integer state;

    public String getId() {
        return id;
    }

    public String getLoc() {
        return loc;
    }

    public String getShare_date() {
        return share_date;
    }

    public String getShare_start() {
        return share_start;
    }

    public String getShare_end() {
        return share_end;
    }

    public Integer getState() {
        return state;
    }

    public void setId(String id) {
        this.id = id;

    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setShare_date(String share_date) {
        this.share_date = share_date;
    }

    public void setShare_start(String share_start) {
        this.share_start = share_start;
    }

    public void setShare_end(String share_end) {
        this.share_end = share_end;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
