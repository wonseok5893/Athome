package com.example.athome.reservation_list;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.athome.retrofit.ReservationListResult;
import com.example.athome.retrofit.ReservationListResult_data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<ReservationListResult_data> data;
    ArrayList<ReservationListResult_data> current = new ArrayList<>();
    ArrayList<ReservationListResult_data> past = new ArrayList<>();
    String sharedToken;
    public PagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<ReservationListResult_data> data, String sharedToken) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.data = data;
        this.sharedToken = sharedToken;
        classifyReserve(data);
    }

    private void classifyReserve(ArrayList<ReservationListResult_data> data) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("EEE MMM dd HH:mm", Locale.US);
        String nowTime = sdfNow.format(date);


        String end;
        Date endDate;
        Date nowDate = null;
        try {
            nowDate = sdfNow.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int size = data.size();
        for (int i = 0; i < size; i++) {
            end = data.get(i).getEndTime().substring(0, 16);

            try {
                endDate = sdfNow.parse(end);
                Log.i("jiwon", "[" + i + "] now : " + nowDate.toString() + "end : " + endDate.toString());
                if (nowDate.getTime() < endDate.getTime()) {
                    Log.i("jiwon", "[" + i + "] current");
                    current.add(data.get(i));
                } else if (endDate.getTime() <= nowDate.getTime()) {
                    Log.i("jiwon", "[" + i + "] past");
                    past.add(data.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle;
        switch (position) {
            case 0:
                NowReservFragmnet tab1 = new NowReservFragmnet();
                bundle = new Bundle();
                bundle.putParcelableArrayList("current", current);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                PastReservFragmnet tab2 = new PastReservFragmnet();
                bundle = new Bundle();
                bundle.putParcelableArrayList("past", past);
                bundle.putString("sharedToken",sharedToken);
                tab2.setArguments(bundle);
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