package com.example.athome.shared_time;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.athome.CustomTimePickerDialog;
import com.example.athome.R;
import com.example.athome.main.MainActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SharedParkingTime extends AppCompatActivity {
    private TextView allday, startTime, endTime;
    private Switch allBtn, monBtn, tuesBtn, wenBtn, thurBtn, friBtn, satBtn, sunBtn;
    private Button backBtn;
    private ImageView startEdit, endEdit;

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
        monBtn = findViewById(R.id.monday_switch);
        tuesBtn = findViewById(R.id.tuesday_switch);
        wenBtn = findViewById(R.id.wensday_switch);
        thurBtn = findViewById(R.id.thursday_switch);
        friBtn = findViewById(R.id.friday_switch);
        satBtn = findViewById(R.id.saturday_switch);
        sunBtn = findViewById(R.id.sunday_switch);
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
                }
            }
        };


        backBtn.setOnClickListener(Listener);
        startEdit.setOnClickListener(Listener);
        endEdit.setOnClickListener(Listener);
    }


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

    //요일별 스위치 클릭시
    public void setChecked(boolean checked) {
        View.OnClickListener Listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.allday_switch:
                        allBtn.setChecked(true);
                    case R.id.monday_switch:
                        monBtn.setChecked(true);
                        break;
                    case R.id.tuesday_switch:
                        tuesBtn.setChecked(true);
                        break;
                    case R.id.wensday_switch:
                        wenBtn.setChecked(true);
                        break;
                    case R.id.thursday_switch:
                        thurBtn.setChecked(true);
                        break;
                    case R.id.friday_switch:
                        friBtn.setChecked(true);
                        break;
                    case R.id.saturday_switch:
                        satBtn.setChecked(true);
                        break;
                    case R.id.sunday_switch:
                        sunBtn.setChecked(true);
                        break;
                }

            }
        };
    }
}


