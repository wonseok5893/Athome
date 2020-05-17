package com.example.athome.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.example.athome.R;

public class SettingParkingActivity  extends AppCompatActivity {
    private Button btn_back_setting_parking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_parking);

        btn_back_setting_parking=(Button)findViewById(R.id.btn_back_setting_parking);
        btn_back_setting_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
