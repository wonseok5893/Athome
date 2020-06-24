package com.example.athome.shared_time;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.athome.R;
import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.myParkingResult;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class SharedParkingList extends AppCompatActivity {
    private Button btn_my_reserve_list;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private myParkingResult myRes;
    private ArrayList<myParkingResult.Data> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_list);
        this.api(); //data 초기화
        this.InitializeView();
        this.SetListener();

        final ViewPager viewPager = (ViewPager) findViewById(R.id.my_reserve_list_viewpager);
        final SharedListPagerAdapter adapter = new SharedListPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), data);
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

    private void api() {
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");


        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiService serviceApis = new RestRequestHelper().getApiService();
                Call<myParkingResult> res = serviceApis.requestParkingReserveList(sharedToken,"capstone");

                try {
                    myRes = res.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (myRes != null) {
            if (myRes.getResult().equals("success")) {
                data = (ArrayList<myParkingResult.Data>) myRes.getData();
            } else {
                Toast.makeText(this, myRes.getmessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "정보를 받아 오지 못했습니다.", Toast.LENGTH_SHORT).show();
        }
        Log.i("share",data.get(1).getEndTime());
    }


    public void InitializeView() {
        btn_my_reserve_list = (Button) findViewById(R.id.btn_my_reserve_list);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.my_reserve_list_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("현재내역"));
        tabLayout.addTab(tabLayout.newTab().setText("지난내역"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void SetListener() {
        View.OnClickListener Listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_my_reserve_list: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
                        break;

                }
            }
        };
        btn_my_reserve_list.setOnClickListener(Listener);
    }
}
