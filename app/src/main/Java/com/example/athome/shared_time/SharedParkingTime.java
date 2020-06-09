package com.example.athome.shared_time;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.athome.reservation.CustomTimePickerDialog;
import com.example.athome.R;

public class SharedParkingTime extends AppCompatActivity {
    private TextView allday, startTime, endTime;
    private Button backBtn;
    private ImageButton saveBtn;
    private Switch allBtn;

    private Button[] dayButton = new Button[7];
    private int[] dayState = new int[7];

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
        dayButton[0] = findViewById(R.id.sunday);
        dayButton[1] = findViewById(R.id.monday);
        dayButton[2] = findViewById(R.id.tuesday);
        dayButton[3] = findViewById(R.id.wensday);
        dayButton[4] = findViewById(R.id.thursday);
        dayButton[5] = findViewById(R.id.friday);
        dayButton[6] = findViewById(R.id.saturday);
        backBtn = findViewById(R.id.share_time_backBtn);
        saveBtn = findViewById(R.id.time_saveBtn);
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
                    case R.id.sunday:
                        // 일요일이 true이면
                        if(dayState[0]==1) {
                            dayState[0]=0;
                            dayButton[0].setSelected(false);
                        } else {
                            dayState[0]=1;
                            dayButton[0].setSelected(true);
                        }
                        break;
                    case R.id.monday:
                        if(dayState[1]==1) {
                            dayState[1]=0;
                            dayButton[1].setSelected(false);
                        } else {
                            dayState[1]=1;
                            dayButton[1].setSelected(true);
                        }
                        break;
                    case R.id.tuesday:
                        if(dayState[2]==1) {
                            dayState[2]=0;
                            dayButton[2].setSelected(false);
                        } else {
                            dayState[2]=1;
                            dayButton[2].setSelected(true);
                        }
                        break;
                    case R.id.wensday:
                        if(dayState[3]==1) {
                            dayState[3]=0;
                            dayButton[3].setSelected(false);
                        } else {
                            dayState[3]=1;
                            dayButton[3].setSelected(true);
                        }
                        break;
                    case R.id.thursday:
                        if(dayState[4]==1) {
                            dayState[4]=0;
                            dayButton[4].setSelected(false);
                        } else {
                            dayState[4]=1;
                            dayButton[4].setSelected(true);
                        }
                        break;
                    case R.id.friday:
                        if(dayState[5]==1) {
                            dayState[5]=0;
                            dayButton[5].setSelected(false);
                        } else {
                            dayState[5]=1;
                            dayButton[5].setSelected(true);
                        }
                        break;
                    case R.id.saturday:
                        if(dayState[6]==1) {
                            dayState[6]=0;
                            dayButton[6].setSelected(false);
                        } else {
                            dayState[6]=1;
                            dayButton[6].setSelected(true);
                        }
                        break;


                }
            }
        };


        backBtn.setOnClickListener(Listener);
        startTime.setOnClickListener(Listener);
        endTime.setOnClickListener(Listener);

        for(int i=0;i<7;i++) {
            dayButton[i].setOnClickListener(Listener);
        }

        allBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                }
                else{
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
