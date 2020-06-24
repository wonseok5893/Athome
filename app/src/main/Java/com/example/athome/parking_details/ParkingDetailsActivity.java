package com.example.athome.parking_details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.athome.R;
import com.example.athome.main.MainActivity;
import com.example.athome.reservation.ReserveActivity;
import com.example.athome.reservation_list.ParkingDetail;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.LocationInfoList;
import com.example.athome.retrofit.MarkerResult;
import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.retrofit.getShareImageResult;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingDetailsActivity extends AppCompatActivity {
    private Button btn_back_parking_details;
    private Button btn_reserv;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private int[] timeArray;
    private String startTime, endTime, parkingInfo, locationName,description;
    private TextView parking_details_text;
    private String locationId;
    private double longitude;
    private double latitude;
    private ParkingDetail pdr;
    private getShareImageResult ImgRes;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.InitializeView();
        this.SetListner();

        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");

        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<getShareImageResult> res = serviceApi.fetchCaptcha(locationId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ImgRes = res.execute().body();
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

        if (ImgRes != null) {
            uri = ImgRes.getFilePath();
        } else {

        }
        Log.i("jiwon", uri);

        parking_details_text.setText(parkingInfo);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.parking_details_viewpager);
        final DetailsPagerAdapter adapter = new DetailsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),
                timeArray, startTime, endTime, locationName, parkingInfo, uri, description);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
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


        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<ParkingDetail> res = serviceApi.requestParkingDetail("", parkingInfo);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pdr = res.execute().body();
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

        timeArray = intent.getIntArrayExtra("timeArray");
        parkingInfo = intent.getStringExtra("parkingInfo");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        locationId = intent.getStringExtra("locationId");
        locationName = intent.getStringExtra("locationName");
        longitude = intent.getDoubleExtra("longitude", 0);
        latitude = intent.getDoubleExtra("latitude", 0);
        description = intent.getStringExtra("description");

        btn_back_parking_details = (Button) findViewById(R.id.btn_back_parking_details);
        btn_reserv = (Button) findViewById(R.id.btn_reserv);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.parking_details_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("기본정보"));
        tabLayout.addTab(tabLayout.newTab().setText("요금정보"));
        tabLayout.addTab(tabLayout.newTab().setText("시간정보"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        parking_details_text = findViewById(R.id.parking_details_text);
    }

    public void SetListner() {
        View.OnClickListener Listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_back_parking_details: //뒤로가기 버튼
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);

                        break;
                    case R.id.btn_reserv: //예약하기버튼
                        intent = new Intent(getApplicationContext(), ReserveActivity.class);
                        intent.putExtra("locationId", locationId);//_id
                        intent.putExtra("locationName", locationName); // 주소
                        intent.putExtra("userId", MainActivity.getUser().getUserId()); // UserId
                        intent.putExtra("latitude", latitude); // 위도
                        intent.putExtra("longitude", longitude); // 경도
                        intent.putExtra("parkingInfo", parkingInfo); // 구획 번호

                        intent.putExtra("locationStartTime", startTime);
                        intent.putExtra("locationEndTime", endTime);
                        intent.putExtra("locationDaySet", timeArray);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        break;

                }
            }
        };
        btn_back_parking_details.setOnClickListener(Listener);
        btn_reserv.setOnClickListener(Listener);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ParkingDetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
