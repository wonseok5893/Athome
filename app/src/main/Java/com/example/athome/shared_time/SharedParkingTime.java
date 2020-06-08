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

import com.example.athome.reservation.CustomTimePickerDialog;
import com.example.athome.R;
import com.example.athome.main.MainActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SharedParkingTime extends AppCompatActivity {
    private TextView allday, startTime, endTime;
    private Button monBtn, tuesBtn, wenBtn, thurBtn, friBtn, satBtn, sunBtn;
    private Button backBtn;
    private Switch allBtn;
    boolean monState = true;
    boolean tueState = true;
    boolean wenState = true;
    boolean thurState = true;
    boolean friState = true;
    boolean satState = true;
    boolean sunState = true;
    boolean allState = true;


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

    public void Initialize() {
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
                    case R.id.share_time_start:
                        showDialog(START_TIME_DIALOG_ID);
                        break;
                    case R.id.share_time_end: //예약 종료시간 설정정
                        showDialog(END_TIME_DIALOG_ID);
                        break;


                }
            }
        };


        backBtn.setOnClickListener(Listener);
        startTime.setOnClickListener(Listener);
        endTime.setOnClickListener(Listener);
        monBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monBtn.setSelected(true);
            }
        });

        tuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuesBtn.setSelected(true);
            }
        });

        wenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wenBtn.setSelected(true);
            }
        });

        thurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thurBtn.setSelected(true);
            }
        });

        friBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friBtn.setSelected(true);
            }
        });

        satBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                satBtn.setSelected(true);
            }
        });

        sunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sunBtn.setSelected(true);
            }
        });

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

