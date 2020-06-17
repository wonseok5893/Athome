package com.example.athome.parking_details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.athome.R;
import com.example.athome.reservation.ReserveActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

public class ParkingDetailsActivity extends AppCompatActivity {
    private Button btn_back_parking_details;
    private Button btn_reserv;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private int[] timeArray;
    private String startTime, endTime, parkingInfo, locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.InitializeView();
        this.SetListner();

        final ViewPager viewPager=(ViewPager)findViewById(R.id.parking_details_viewpager);
        final DetailsPagerAdapter adapter = new  DetailsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                timeArray, startTime, endTime, locationName, parkingInfo);
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

        Intent intent = getIntent();
        timeArray = intent.getIntArrayExtra("timeArray");
        startTime= intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        parkingInfo = intent.getStringExtra("parkingInfo");
        locationName = intent.getStringExtra("locationName");


        btn_back_parking_details = (Button) findViewById(R.id.btn_back_parking_details);
        btn_reserv=(Button)findViewById(R.id.btn_reserv);
        mViewPager=(ViewPager)findViewById(R.id.container);
        tabLayout=(TabLayout)findViewById(R.id.parking_details_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("기본정보"));
        tabLayout.addTab(tabLayout.newTab().setText("요금정보"));
        tabLayout.addTab(tabLayout.newTab().setText("시간정보"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_parking_details: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.btn_reserv: //예약하기버튼
                        Intent intent = new Intent(getApplicationContext(), ReserveActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        };
        btn_back_parking_details.setOnClickListener(Listener);
        btn_reserv.setOnClickListener(Listener);

    }
}
