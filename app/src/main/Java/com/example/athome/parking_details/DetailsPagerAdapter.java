package com.example.athome.parking_details;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.athome.reservation_list.NowReservFragmnet;
import com.example.athome.reservation_list.PastReservFragmnet;

public class DetailsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public DetailsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BasicInfoFragment tab1 = new BasicInfoFragment();
                return tab1;
            case 1:
                PricingInfoFragment tab2 = new PricingInfoFragment();
                return tab2;
            case 2:
                TimeInfoFragment tab3 = new TimeInfoFragment();
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
