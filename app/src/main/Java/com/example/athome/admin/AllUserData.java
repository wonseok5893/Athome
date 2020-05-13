package com.example.athome.admin;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllUserData implements Parcelable {
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("reservation")
    @Expose
    private List<Object> reservation = null;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("__v")
    @Expose
    private Integer v;

    protected AllUserData(Parcel in) {
        if (in.readByte() == 0) {
            point = null;
        } else {
            point = in.readInt();
        }
        if (in.readByte() == 0) {
            state = null;
        } else {
            state = in.readInt();
        }
        id = in.readString();
        userId = in.readString();
        userPassword = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userPhone = in.readString();
        created = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (point == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(point);
        }
        if (state == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(state);
        }
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(userPassword);
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(userPhone);
        dest.writeString(created);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllUserData> CREATOR = new Creator<AllUserData>() {
        @Override
        public AllUserData createFromParcel(Parcel in) {
            return new AllUserData(in);
        }

        @Override
        public AllUserData[] newArray(int size) {
            return new AllUserData[size];
        }
    };

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public List<Object> getReservation() {
        return reservation;
    }

    public void setReservation(List<Object> reservation) {
        this.reservation = reservation;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
