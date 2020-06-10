package com.example.athome.payment_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.athome.parking_details.BasicInfoFragment;
import com.example.athome.parking_details.PricingInfoFragment;
import com.example.athome.parking_details.TimeInfoFragment;
import com.example.athome.reservation_list.NowReservFragmnet;
import com.example.athome.reservation_list.PastReservFragmnet;
import com.example.athome.retrofit.ReservationListResult_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PayListPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PayListPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PayListFragment tab1 = new  PayListFragment();
                return tab1;
            case 1:
                ChargeListFragment tab2 = new ChargeListFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}