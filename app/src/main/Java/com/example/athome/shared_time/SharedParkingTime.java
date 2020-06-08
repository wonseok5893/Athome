package com.example.athome.shared_time;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.athome.RestRequestHelper;
import com.example.athome.reservation.CustomTimePickerDialog;
import com.example.athome.R;
import com.example.athome.main.MainActivity;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.ReserveListResult;
import com.example.athome.retrofit.ShareInfoResult;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

public class SharedParkingTime extends AppCompatActivity {
    private TextView allday, startTime, endTime;
    private Button monBtn, tuesBtn, wenBtn, thurBtn, friBtn, satBtn, sunBtn;
    private Button backBtn;
    private ImageView startEdit, endEdit;
    private Switch allBtn;
    private boolean monState = false;
    private boolean tueState = false;
    private boolean wenState = false;
    private boolean thurState = false;
    private boolean friState = false;
    private boolean satState = false;
    private boolean sunState = false;
    private boolean allState = false;

    private ShareInfoResult shInfo;


    static final int START_TIME_DIALOG_ID = 1;
    static final int END_TIME_DIALOG_ID = 2;

    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_time);
        this.Initialize();
        this.setListner();

    }

    @Override
    protected void onStop() {
        super.onStop();

        // 패킷 보내주는거 작성
    }

    public void Initialize() {

        Intent intent = getIntent();

        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<ShareInfoResult> res = serviceApi.getShareData(intent.getStringExtra("token"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    shInfo = res.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        startTime = findViewById(R.id.share_time_start);
        endTime = findViewById(R.id.share_time_end);
        allBtn = findViewById(R.id.allday_switch);
        monBtn = findViewById(R.id.monday);
        tuesBtn = findViewById(R.id.tuesday);
        wenBtn = findViewById(R.id.wensday);
        thurBtn = findViewById(R.id.thursday);
        friBtn = findViewById(R.id.friday);
        satBtn = findViewById(R.id.saturday);
        sunBtn = findViewById(R.id.sunday);
        backBtn = findViewById(R.id.share_time_backBtn);
        startEdit = findViewById(R.id.start_editBtn);
        endEdit = findViewById(R.id.end_editBtn);

    }

    public void setListner() {
        View.OnClickListener Listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.share_time_backBtn:
                        finish();
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
                        break;
                    case R.id.start_editBtn:
                        showDialog(START_TIME_DIALOG_ID);
                        break;
                    case R.id.end_editBtn: //예약 종료시간 설정정
                        showDialog(END_TIME_DIALOG_ID);
                        break;
                    case R.id.monday:
                        if(monState==true) {
                            monState=true;
                            monBtn.setSelected(false);
                        } else {
                            monState=false;
                            monBtn.setSelected(true);
                        }
                        break;
                }
            }
        };


        backBtn.setOnClickListener(Listener);
        startEdit.setOnClickListener(Listener);
        endEdit.setOnClickListener(Listener);














        allBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    monBtn.setSelected(false);
                    tuesBtn.setSelected(false);
                    wenBtn.setSelected(false);
                    thurBtn.setSelected(false);
                    friBtn.setSelected(false);
                    satBtn.setSelected(false);
                    sunBtn.setSelected(false);
                }
                else{
                    monBtn.setSelected(true);
                    tuesBtn.setSelected(true);
                    wenBtn.setSelected(true);
                    thurBtn.setSelected(true);
                    friBtn.setSelected(true);
                    satBtn.setSelected(true);
                    sunBtn.setSelected(true);
                }
            }
        });

    }


    //요일별 스위치 클릭시


    //StartTimePicker 리스너
    private CustomTimePickerDialog.OnTimeSetListener mStartTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    startTime.setText(String.format("%d시 %d분", mHour, mMinute));
                }
            };

    //EndTimePicker 리스너
    private CustomTimePickerDialog.OnTimeSetListener mEndTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    endTime.setText(String.format("%d시 %d분", mHour, mMinute));
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_TIME_DIALOG_ID:
                return new CustomTimePickerDialog(this, mStartTimeSetListener, mHour, mMinute, true);

            case END_TIME_DIALOG_ID:
                return new CustomTimePickerDialog(this, mEndTimeSetListener, mHour, mMinute, true);
        }

        return null;
    }
}

