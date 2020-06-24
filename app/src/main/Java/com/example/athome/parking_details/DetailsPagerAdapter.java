package com.example.athome.parking_details;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.athome.reservation_list.NowReservFragmnet;
import com.example.athome.reservation_list.PastReservFragmnet;

import java.util.Arrays;

public class DetailsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    private int[] timeArray;
    private String startTime, endTime, parkingInfo, locationName,description;
    private String uri;

    public DetailsPagerAdapter(FragmentManager fm, int NumOfTabs,
                               int[] timeArray, String startTime, String endTime, String locationName, String parkingINfo, String uri, String description) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.timeArray = timeArray;
        this.startTime= startTime;
        this.endTime = endTime;
        this.locationName = locationName;
        this.parkingInfo = parkingINfo;
        this.uri = uri;
        this.description = description;
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                BasicInfoFragment tab1 = new BasicInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("locationName", locationName); // Key, Value
                bundle.putString("parkingInfo", parkingInfo); // Key, Value
                bundle.putString("uri",this.uri);
                bundle.putString("description",description);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                PricingInfoFragment tab2 = new PricingInfoFragment();
                return tab2;
            case 2:
                TimeInfoFragment tab3 = new TimeInfoFragment();
                Bundle bundles = new Bundle();
                bundles.putIntArray("timeArray", timeArray);
                bundles.putString("startTime", startTime); // Key, Value
                bundles.putString("endTime", endTime); // Key, Value

                tab3.setArguments(bundles);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
