package com.example.athome.shared_time;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.athome.R;
import com.example.athome.payment_list.PayListPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SharedParkingList extends AppCompatActivity  {
    private Button btn_my_reserve_list;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_list);

        this.InitializeView();
        this.SetListner();

        final ViewPager viewPager=(ViewPager)findViewById(R.id.my_reserve_list_viewpager);
        final SharedListPagerAdapter adapter = new  SharedListPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    public void InitializeView() {
        btn_my_reserve_list = (Button) findViewById(R.id.btn_my_reserve_list);
        mViewPager=(ViewPager)findViewById(R.id.container);
        tabLayout=(TabLayout)findViewById(R.id.my_reserve_list_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("현재내역"));
        tabLayout.addTab(tabLayout.newTab().setText("지난내역"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_my_reserve_list: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;

                }
            }
        };
        btn_my_reserve_list.setOnClickListener(Listener);

    }
    }
