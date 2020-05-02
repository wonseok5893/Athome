package com.example.athome.reservation_list;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.athome.R;

public class ReservListActivity extends AppCompatActivity {
    private Button btn_back_reserv;
    private Button btn_now_reserv;
    private Button btn_past_reserv;
    private FragmentManager fragmentManager;
    private NowReservFragmnet nowReservFragmnet;
    private PastReservFragmnet pastReservFragmnet;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_list);

        btn_now_reserv=(Button)findViewById(R.id.btn_now_reserv) ;
        btn_past_reserv=(Button)findViewById(R.id.btn_past_reserv);
        btn_back_reserv = (Button) findViewById(R.id.btn_back_reserv);

        fragmentManager = getSupportFragmentManager();
        nowReservFragmnet = new NowReservFragmnet();
        pastReservFragmnet = new PastReservFragmnet();

        btn_back_reserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        //예약내역 클릭했을때 현재내역이 첫화면으로 나와야함
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.reserv_list_FrameLayout, nowReservFragmnet).commitAllowingStateLoss();

        //현재내역버튼 클릭
        btn_now_reserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_now_reserv.setBackgroundColor(getResources().getColor(R.color.babypink));
                btn_past_reserv.setBackgroundColor(getResources().getColor(R.color.white));
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.reserv_list_FrameLayout, nowReservFragmnet).commitAllowingStateLoss();
            }
        });

        //지난내역버튼 클릭
        btn_past_reserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_now_reserv.setBackgroundColor(getResources().getColor(R.color.white));
                btn_past_reserv.setBackgroundColor(getResources().getColor(R.color.babypink));
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.reserv_list_FrameLayout, pastReservFragmnet).commitAllowingStateLoss();
            }
        });



    }



}
