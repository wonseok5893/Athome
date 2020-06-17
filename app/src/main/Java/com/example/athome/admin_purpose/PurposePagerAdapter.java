package com.example.athome.admin_purpose;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PurposePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public  PurposePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PurposePieFragment tab1 = new PurposePieFragment();
                return tab1;
            case 1:
                PurposePieFragment tab2 = new PurposePieFragment();
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