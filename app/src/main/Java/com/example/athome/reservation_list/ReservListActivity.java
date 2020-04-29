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
    private FragmentManager fragmentManager;
    private NowReservFragmnet nowReservFragmnet;
    private PastReservFragmnet pastReservFragmnet;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_list);

        btn_back_reserv = (Button) findViewById(R.id.btn_back_reserv);
        btn_back_reserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        fragmentManager = getSupportFragmentManager();
        nowReservFragmnet = new NowReservFragmnet();
        pastReservFragmnet = new PastReservFragmnet();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.reserv_list_FrameLayout, nowReservFragmnet).commitAllowingStateLoss();
    }


    public void clickHandler(View view){
        transaction = fragmentManager.beginTransaction();
        switch (view.getId())
        {
            case R.id.btn_now_reserv:
                transaction.replace(R.id.reserv_list_FrameLayout, nowReservFragmnet).commitAllowingStateLoss();
                break;
            case R.id.btn_past_reserv:
                transaction.replace(R.id.reserv_list_FrameLayout, pastReservFragmnet).commitAllowingStateLoss();
                break;
        }

    }
}
