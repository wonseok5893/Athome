package com.example.athome.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarkerResult {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public class Data{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("owner")
        @Expose
        private Owner owner;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("parkingInfo")
        @Expose
        private String parkingInfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getParkingInfo() {
            return parkingInfo;
        }

        public void setParkingInfo(String parkingInfo) {
            this.parkingInfo = parkingInfo;
        }

        public class Owner{
            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("userId")
            @Expose
            private String userId;
            @SerializedName("userPhone")
            @Expose
            private String userPhone;

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

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }
        }
    }
}

