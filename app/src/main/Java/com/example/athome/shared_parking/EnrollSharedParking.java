package com.example.athome.shared_parking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;
import com.example.athome.User;
import com.example.athome.main.MainActivity;
import com.example.athome.reservation_list.ReservListActivity;
import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;


public class EnrollSharedParking extends AppCompatActivity {
    private TextView assigner_phone_value;
    private EditText assigner_birth_value;
    private EditText sign_id;
    private TextView assigner_location_value;
    private Button btn_back_assigner_lookup;
    private Button btn_assigner_lookup; //구청에서 배정자 조회확인이 된후에 버튼이 눌려짐
    private String locationName;
    private LatLng SelectLocation;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_assigner_lookup);

        assigner_phone_value = (TextView) findViewById(R.id.assigner_phone_value);
        assigner_birth_value = (EditText) findViewById(R.id.assigner_birth_value);
        sign_id = (EditText) findViewById(R.id.sign_id);
        assigner_location_value = (TextView) findViewById(R.id.assigner_location_value);
        btn_back_assigner_lookup = (Button) findViewById(R.id.btn_back_assigner_lookup);
        btn_assigner_lookup = (Button) findViewById(R.id.btn_assigner_lookup);

        //이전 액티비티 데이터 받기
        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");
        locationName = intent.getStringExtra("LocationName");
        SelectLocation = intent.getParcelableExtra("SelectLocation");

        //배정자가 지정한 주소값 입력
        assigner_location_value.setText(locationName);
        assigner_phone_value.setText(user.getUserPhone());

        //뒤로가기 버튼
        btn_back_assigner_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });

        // 하단 [확인] 버튼
        btn_assigner_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SharedParkingExplanation.class);

                // 사진 등록 액티비티로 배정자 정보 전달
                String birth = assigner_birth_value.getText().toString();
                String sign = sign_id.getText().toString();

                if (birth.length() == 0 || sign.length() == 0) {
                    Toast.makeText(EnrollSharedParking.this, "생일 또는 차량번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra("birth", birth);
                    intent.putExtra("carNum", sign);
                    intent.putExtra("locationName", locationName);
                    intent.putExtra("SelectLocation",SelectLocation);

                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
                }
            }
        });
    }
}
