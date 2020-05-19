package com.example.athome;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.retrofit.ApiService;

import java.util.Calendar;
import java.util.Date;

public class ReserveActivity extends AppCompatActivity {
    private Button btn_back_reserv;
    private TextView parking_number;
    private TextView reserv_date_select;
    private TextView reserv_start_time_select;
    private TextView reserv_end_time_select;
    private TextView parking_car_number_select;

    private Calendar c;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    static final int DATE_DIALOG_ID=0;
    static final int START_TIME_DIALOG_ID=1;
    static final int END_TIME_DIALOG_ID=2;

    private int month=0;
    private int year=0;
    private int day=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        this.InitializeView();
        this.SetListner();
    }



    public void InitializeView(){
        btn_back_reserv=findViewById(R.id.btn_back_reserv);
        parking_number=findViewById(R.id.parking_number);
        reserv_date_select=findViewById(R.id.reserv_date_select);
        reserv_start_time_select=findViewById(R.id.reserv_start_time_select);
        reserv_end_time_select=findViewById(R.id.reserv_end_time_select);

        //현재 날짜와 시간 가져오기
        c=Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_reserv: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.reserv_date_select: //예약날짜 설정
                        showDialog(DATE_DIALOG_ID);
                        break;
                    case R.id.reserv_start_time_select: //예약 시작시간 설정
                        showDialog(START_TIME_DIALOG_ID);
                        break;
                    case R.id.reserv_end_time_select: //예약 종료시간 설정정
                        showDialog(END_TIME_DIALOG_ID);
                        break;
               }
            }
        };
        btn_back_reserv.setOnClickListener(Listener);
        reserv_date_select.setOnClickListener(Listener);
        reserv_start_time_select.setOnClickListener(Listener);
        reserv_end_time_select.setOnClickListener(Listener);
    }

    //DatePicker 리스너
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    reserv_date_select.setText(String.format("%d년 %d월 %d일",mYear,mMonth+1,mDay));
                }
            };

    //StartTimePicker 리스너
    private TimePickerDialog.OnTimeSetListener mStartTimeSetListener =
            new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    reserv_start_time_select.setText(String.format("%d시 %d분",mHour,mMinute));
                }
            };

    //EndTimePicker 리스너
    private TimePickerDialog.OnTimeSetListener mEndTimeSetListener =
            new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    reserv_end_time_select.setText(String.format("%d시 %d분",mHour,mMinute));
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);

            case START_TIME_DIALOG_ID :
                return new TimePickerDialog(this, mStartTimeSetListener, mHour, mMinute, false);

            case END_TIME_DIALOG_ID:
                return new TimePickerDialog(this, mEndTimeSetListener, mHour, mMinute, false);
        }

        return null;
    }



}
