package com.example.athome.shared_time;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.athome.parking_details.BasicInfoFragment;
import com.example.athome.parking_details.PricingInfoFragment;
import com.example.athome.parking_details.TimeInfoFragment;
import com.example.athome.payment_list.ChargeListFragment;
import com.example.athome.payment_list.PayListFragment;
import com.example.athome.reservation_list.NowReservFragmnet;
import com.example.athome.reservation_list.PastReservFragmnet;
import com.example.athome.retrofit.ReservationListResult_data;
import com.example.athome.retrofit.myParkingResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SharedListPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<myParkingResult.Data> data = null;
    ArrayList<myParkingResult.Data> current = new ArrayList<>();
    ArrayList<myParkingResult.Data> past = new ArrayList<>();

    public SharedListPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<myParkingResult.Data> data) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        // 원하는 시간으로 파싱
        SimpleDateFormat sdfNow = new SimpleDateFormat("EEE MMM dd HH:mm", Locale.US);

        //현재 시각 date 변수 구함
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        String nowTime = sdfNow.format(date);
        Date nowDate = null;
        try {
            nowDate = sdfNow.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String end;
        Date endDate;
        if(data != null){
            for(myParkingResult.Data r : data){
                end = r.getEndTime().substring(0, 16);
                try {
                    endDate = sdfNow.parse(end);
                    if(nowDate.getTime() >= endDate.getTime()){
                        past.add(r);
                    }else{
                        current.add(r);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }




        switch (position) {
            case 0:
                MyNowListFragment tab1 = new  MyNowListFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("current",current);
                tab1.setArguments(bundle1);
                return tab1;
            case 1:
                MyPastListFragment tab2 = new MyPastListFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("past",past);
                tab2.setArguments(bundle2);
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