package com.example.athome.non_member;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.account.AccountCarRegister;
import com.example.athome.account.CarListAdapter;
import com.example.athome.account.ItemAccountCarData;
import com.example.athome.reservation.CustomTimePickerDialog;
import com.example.athome.reservation.ReserveActivity;
import com.example.athome.retrofit.ApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class nonReserveActivity extends AppCompatActivity {
    private LinearLayout reserv_start;
    private LinearLayout reserv_end;
    private Button btn_back_reserv;
    private Button btn_next_reserv;
    private TextView parking_number;
    private TextView reserv_start_date_select;
    private TextView reserv_end_date_select;
    private TextView reserv_start_time_select;
    private TextView reserv_end_time_select;
    private EditText non_car_id;
    private EditText non_name_id;
    private EditText non_phnum_id;

    private Calendar c;
    private int smYear;
    private int smMonth;
    private int smDay;

    private int emYear;
    private int emMonth;
    private int emDay;

    private int smHour;
    private int smMinute;
    private int emHour;
    private int emMinute;

    private String _id;
    private String userId;
    private Double latitude;
    private Double longitude;
    private String sharedToken;
    private Date startTime;
    private Date endTime;

    static final int START_DATE_DIALOG_ID=0;
    static final int END_DATE_DIALOG_ID=1;
    static final int START_TIME_DIALOG_ID=2;
    static final int END_TIME_DIALOG_ID=3;
    static final String TEXT = "text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.non_member_reserve);

        this.InitializeView();
        this.SetListner();
        Intent intent = getIntent();
        _id = intent.getStringExtra("locationId");
        latitude = intent.getDoubleExtra("latitude",0);
        longitude = intent.getDoubleExtra("longitude",0);
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        sharedToken = sf.getString("token", "");
    }



    public void InitializeView(){
        reserv_start=findViewById(R.id.non_reserv_start);
        reserv_end=findViewById(R.id.non_reserv_end);

        btn_back_reserv=findViewById(R.id.btn_back_non_reserv);
        parking_number=findViewById(R.id.non_parking_number);
        reserv_start_date_select=findViewById(R.id.non_reserv_start_date_select);
        reserv_end_date_select=findViewById(R.id.non_reserv_end_date_select);

        reserv_start_time_select=findViewById(R.id.non_reserv_start_time_select);
        reserv_end_time_select=findViewById(R.id.non_reserv_end_time_select);

        non_car_id = findViewById(R.id.non_car_id);
        non_name_id = findViewById(R.id.non_name_id);
        non_phnum_id = findViewById(R.id.non_phnum_id);
        btn_next_reserv=findViewById(R.id.btn_next_non_reserv);


        //현재 날짜와 시간 가져오기
        c=Calendar.getInstance();
        smYear = c.get(Calendar.YEAR);
        smMonth = c.get(Calendar.MONTH);
        smDay = c.get(Calendar.DAY_OF_MONTH);
        emYear = c.get(Calendar.YEAR);
        emMonth = c.get(Calendar.MONTH);
        emDay = c.get(Calendar.DAY_OF_MONTH);
        smHour = c.get(Calendar.HOUR_OF_DAY);
        smMinute = c.get(Calendar.MINUTE);
        emHour = c.get(Calendar.HOUR_OF_DAY);
        emMinute = c.get(Calendar.MINUTE);


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
                    case R.id.non_reserv_start://예약시작날짜와시간설정
                        showDialog(START_DATE_DIALOG_ID);
                        break;
                    case R.id.non_reserv_end: //예약종료날짜와시간설정
                        showDialog(END_DATE_DIALOG_ID);
                        break;

                    case R.id.btn_next_non_reserv:
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String startTimeString = smYear+"-"+(smMonth+1)+"-"+smDay+" "+smHour+":"+smMinute;//"2013-04-08 10:10";
                        String endTimeString = emYear+"-"+(emMonth+1)+"-"+emDay +" "+emHour+":"+emMinute;//"2013-04-08 10:10";
                        try {
                            startTime = transFormat.parse(startTimeString);
                            endTime = transFormat.parse(endTimeString);
                            Log.d("time",startTime.toString());
                            Log.d("time",endTime.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String nonCarnum = non_car_id.getText().toString();
                        String nonName = non_name_id.getText().toString();
                        String nonPhnum = non_phnum_id.getText().toString();
                        ApiService apiService = new RestRequestHelper().getApiService();
                        Call<ResponseBody> res = apiService.sendReserve(sharedToken, _id, nonCarnum, startTime,endTime);
                        res.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(nonReserveActivity.this,"예약 되셨습니다.",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(nonReserveActivity.this,"다시 시도해주십시오.",Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
        };
        btn_back_reserv.setOnClickListener(Listener);
        reserv_start.setOnClickListener(Listener);
        reserv_end.setOnClickListener(Listener);
    }


    //StartDatePicker 리스너
    private DatePickerDialog.OnDateSetListener mStartDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    smYear = year;
                    smMonth = monthOfYear;
                    smDay = dayOfMonth;
                    showDialog(START_TIME_DIALOG_ID);
                    reserv_start_date_select.setText(String.format("%-d-%d-%d",smYear,smMonth+1,smDay));

                }
            };

    //StartDatePicker 리스너
    private DatePickerDialog.OnDateSetListener mEndDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    emYear = year;
                    emMonth = monthOfYear;
                    emDay = dayOfMonth;
                    showDialog(END_TIME_DIALOG_ID);
                    reserv_end_date_select.setText(String.format("%d-%d-%d",emYear,emMonth+1,emDay));

                }
            };

    //StartTimePicker 리스너
    private CustomTimePickerDialog.OnTimeSetListener mStartTimeSetListener =
            new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    smHour = hourOfDay;
                    smMinute = minute;
                    reserv_start_time_select.setText(String.format("%d시 %d분",smHour,smMinute));
                }
            };

    //EndTimePicker 리스너
    private CustomTimePickerDialog.OnTimeSetListener mEndTimeSetListener =
            new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    emHour = hourOfDay;
                    emMinute = minute;
                    reserv_end_time_select.setText(String.format("%d시 %d분",emHour,emMinute));
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_DATE_DIALOG_ID:
                return new DatePickerDialog(this, mStartDateSetListener, smYear, smMonth, smDay);

            case END_DATE_DIALOG_ID:
                return new DatePickerDialog(this, mEndDateSetListener, emYear, emMonth, emDay);

            case START_TIME_DIALOG_ID :
                return new CustomTimePickerDialog(this, mStartTimeSetListener, smHour, smMinute, true);

            case END_TIME_DIALOG_ID:
                return new CustomTimePickerDialog(this, mEndTimeSetListener, emHour, emMinute, true);
        }
        return null;
    }
}
