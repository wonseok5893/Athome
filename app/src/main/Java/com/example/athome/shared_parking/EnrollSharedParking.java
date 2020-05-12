package com.example.athome.shared_parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;
import com.example.athome.reservation_list.ReservListActivity;

import java.util.ArrayList;


public class EnrollSharedParking extends AppCompatActivity {
    private Spinner spinner_location;
    private SpinnerAdapter adapter;
    private Button btn_back_assigner_lookup;
    private Button btn_assigner_lookup; //구청에서 배정자 조회확인이 된후에 버튼이 눌려짐


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_assigner_lookup);

        final ArrayList<String> items = new ArrayList<>();

        //뒤로가기 버튼
        btn_back_assigner_lookup=(Button)findViewById(R.id.btn_back_assigner_lookup);
        btn_back_assigner_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        //
        btn_assigner_lookup=(Button)findViewById(R.id.btn_assigner_lookup);
        btn_assigner_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SharedParkingExplanation.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
            }
        });


        items.add("경기도 수원시");
        items.add("경기도 용인시");
        items.add("위치선택");
        spinner_location = (Spinner) findViewById(R.id.assigner_parking_location_value);

        adapter = new SpinnerAdapter(EnrollSharedParking.this, android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(items);
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
