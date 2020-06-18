package com.example.athome.shared_time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.athome.R;
import com.example.athome.notification.NotificationActivity;
import com.google.android.material.tabs.TabLayout;

public class MyParkingActivity extends AppCompatActivity {
    private Button btn_my_parking;
    private TextView my_time_manage;
    private TextView my_reserve_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_parking);

        this.InitializeView();
        this.SetListner();
    }

    public void InitializeView() {
        btn_my_parking=(Button)findViewById(R.id.btn_my_parking);
        my_time_manage=(TextView)findViewById(R.id.my_time_manage);
        my_reserve_list=(TextView)findViewById(R.id.my_reserve_list);

    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id. btn_my_parking: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.my_time_manage: //시간관리
                        Intent intent = new Intent(getApplicationContext(), SharedParkingTime.class);
                        startActivity(intent);
                        break;
                    case R.id.my_reserve_list: //예약 내역
                        Intent intent1 = new Intent(getApplicationContext(), SharedParkingList.class);
                        startActivity(intent1);

                        break;

                }
            }
        };
        btn_my_parking.setOnClickListener(Listener);
        my_time_manage.setOnClickListener(Listener);
        my_reserve_list.setOnClickListener(Listener);


    }

}
