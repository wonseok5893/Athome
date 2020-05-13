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
import com.example.athome.reservation_list.ReservListActivity;

import java.util.ArrayList;


public class EnrollSharedParking extends AppCompatActivity {
    private Spinner spinner_location;
    private SpinnerAdapter adapter;
    private TextView assigner_phone_value;
    private EditText assigner_birth_value;
    private EditText sign_id;
    private Button btn_back_assigner_lookup;
    private Button btn_assigner_lookup; //구청에서 배정자 조회확인이 된후에 버튼이 눌려짐


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_assigner_lookup);

        assigner_phone_value = (TextView)findViewById(R.id.assigner_phone_value);
        assigner_birth_value = (EditText)findViewById(R.id.assigner_birth_value);
        btn_back_assigner_lookup=(Button)findViewById(R.id.btn_back_assigner_lookup);
        btn_assigner_lookup= (Button)findViewById(R.id.btn_assigner_lookup);
        sign_id = (EditText)findViewById(R.id.sign_id);

        final ArrayList<String> items = new ArrayList<>();

        //뒤로가기 버튼
        btn_back_assigner_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        // 하단 [확인] 버튼
        btn_assigner_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SharedParkingExplanation.class);

                // 사진 등록 액티비티로 배정자 정보 전달
                String phone_num = assigner_phone_value.getText().toString();
                String birth = assigner_birth_value.getText().toString();
                String sign = sign_id.getText().toString();
                String parkLocation = spinner_location.getSelectedItem().toString();

                if(phone_num.length() == 0 || birth.length() == 0 || sign.length() == 0 || parkLocation == items.get(items.size()-1)){
                    Toast.makeText(EnrollSharedParking.this, "정보를 전부 기입해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    intent.putExtra("phNum", phone_num);
                    intent.putExtra("birth", birth);
                    intent.putExtra("carNum", sign);
                    intent.putExtra("parkLocation", parkLocation);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
                }
            }
        });

        items.add("경기도 수원시");
        items.add("경기도 용인시");
        // list items의 마지막 요소는 hint처럼 화면에 표시되고 더 이상 선택되지 않음
        items.add("위치선택");

        adapter = new SpinnerAdapter(EnrollSharedParking.this, android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(items);
        spinner_location = (Spinner) findViewById(R.id.assigner_parking_location_value);
        spinner_location.setAdapter(adapter);
        spinner_location.setSelection(adapter.getCount());
        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
