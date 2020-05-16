package com.example.athome.shared_parking;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.athome.R;
import com.example.athome.User;
import com.naver.maps.geometry.LatLng;


public class MySharedParkingActivity extends AppCompatActivity {
    private Button btn_parking_register;
    private Button btn_back_myparking;
    private LatLng SelectLocation;
    private String locationName;
    private User user;
    /*이곳은 주차장 공유를 처음 시작하는 거라면 주차장공유버튼이 나오고
    공유 주차장을 등록한 후 내 공유한 주차장을 관리하는 곳임*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedparking);

        this.InitializeView();
        this.SetListner();


    }

    public void InitializeView(){
        btn_parking_register=(Button)findViewById(R.id.btn_parking_register) ;
        btn_back_myparking=(Button) findViewById(R.id.btn_back_myparking);
        Intent intent = getIntent();
        SelectLocation = intent.getParcelableExtra("SelectLocation");
        locationName = intent.getStringExtra("LocationName");
        Log.d("Test",locationName);
        user = intent.getParcelableExtra("User");
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_parking_register:
                        Intent intent = new Intent(getApplicationContext(), EnrollSharedParking.class);
                        intent.putExtra("SelectLocation",SelectLocation);
                        intent.putExtra("LocationName",locationName);
                        intent.putExtra("User",user);
                        startActivity(intent);
                        overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);//화면전환시효과
                        break;
                    case R.id.btn_back_myparking: //뒤로가기버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                }
            }
        };
        btn_parking_register.setOnClickListener(Listener);
        btn_back_myparking.setOnClickListener(Listener);
    }

}

