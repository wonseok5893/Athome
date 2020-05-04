package com.example.athome.reservation_list;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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

        this.InitializeView();
        this.SetListner();


    }

    public void InitializeView(){
        btn_now_reserv=(Button)findViewById(R.id.btn_now_reserv) ;
        btn_past_reserv=(Button)findViewById(R.id.btn_past_reserv);
        btn_back_reserv = (Button) findViewById(R.id.btn_back_reserv);
        fragmentManager = getSupportFragmentManager();
        nowReservFragmnet = new NowReservFragmnet();
        pastReservFragmnet = new PastReservFragmnet();

        //예약내역 클릭했을때 현재내역이 첫화면으로 나와야함
        btn_now_reserv.setBackgroundResource(R.drawable.now_reserv_on);
        btn_past_reserv.setBackgroundResource(R.drawable.past_reserv_off);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.reserv_list_FrameLayout, nowReservFragmnet).commitAllowingStateLoss();
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_reserv: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.btn_now_reserv: //현재내역버튼 클릭
                        btn_now_reserv.setBackgroundResource(R.drawable.now_reserv_on);
                        btn_past_reserv.setBackgroundResource(R.drawable.past_reserv_off);
                        transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.reserv_list_FrameLayout, nowReservFragmnet).commitAllowingStateLoss();
                        break;
                    case R.id.btn_past_reserv:
                        btn_now_reserv.setBackgroundResource(R.drawable.now_reserv_off);
                        btn_past_reserv.setBackgroundResource(R.drawable.past_reserv_on);
                        transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.reserv_list_FrameLayout, pastReservFragmnet).commitAllowingStateLoss();
                        break;
                }
            }
        };
        btn_back_reserv.setOnClickListener(Listener);
        btn_now_reserv.setOnClickListener(Listener);
        btn_past_reserv.setOnClickListener(Listener);
    }

}
