package com.example.athome.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;

public class SettingParkingLotActivity extends AppCompatActivity {
    private Button  btn_back_setting_parking_lot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_parking_lot);
        btn_back_setting_parking_lot=(Button)findViewById(R.id. btn_back_setting_parking_lot);
        btn_back_setting_parking_lot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
