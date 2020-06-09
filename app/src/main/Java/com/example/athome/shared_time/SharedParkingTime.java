package com.example.athome.shared_time;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.athome.RestRequestHelper;
import com.example.athome.reservation.CustomTimePickerDialog;
import com.example.athome.R;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.ReserveListResult;
import com.example.athome.retrofit.ShareInfoResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedParkingTime extends AppCompatActivity {

    private ShareInfoResult sir;

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

    void initShareData() {

        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기

        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<ShareInfoResult> res = serviceApi.getShareData(sharedToken, "");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sir = res.execute().body();
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

    }

    void sendShareData() {
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기

        String days = Arrays.toString(dayState);

        ApiService serviceApi = new RestRequestHelper().getApiService();
        Call<ResponseBody> res = serviceApi.sendShareData(sharedToken, days, startTime.getText().toString(), endTime.getText().toString());

        res.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void Initialize() {

        initShareData();

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

        setButtonState();


    }

    void setButtonState() {

        int count = 0;

        while(true) {
            if(dayState[count]==0) {
                dayButton[count].setSelected(true);
            } else {
                dayButton[count].setSelected(false);
            }

            count++;
            if(count==7)
                break;
        }

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
                            dayButton[0].setSelected(true);
                        } else {
                            dayState[0]=1;
                            dayButton[0].setSelected(false);
                        }
                        break;
                    case R.id.monday:
                        if(dayState[1]==1) {
                            dayState[1]=0;
                            dayButton[1].setSelected(true);
                        } else {
                            dayState[1]=1;
                            dayButton[1].setSelected(false);
                        }
                        break;
                    case R.id.tuesday:
                        if(dayState[2]==1) {
                            dayState[2]=0;
                            dayButton[2].setSelected(true);
                        } else {
                            dayState[2]=1;
                            dayButton[2].setSelected(false);
                        }
                        break;
                    case R.id.wensday:
                        if(dayState[3]==1) {
                            dayState[3]=0;
                            dayButton[3].setSelected(true);
                        } else {
                            dayState[3]=1;
                            dayButton[3].setSelected(false);
                        }
                        break;
                    case R.id.thursday:
                        if(dayState[4]==1) {
                            dayState[4]=0;
                            dayButton[4].setSelected(true);
                        } else {
                            dayState[4]=1;
                            dayButton[4].setSelected(false);
                        }
                        break;
                    case R.id.friday:
                        if(dayState[5]==1) {
                            dayState[5]=0;
                            dayButton[5].setSelected(true);
                        } else {
                            dayState[5]=1;
                            dayButton[5].setSelected(false);
                        }
                        break;
                    case R.id.saturday:
                        if(dayState[6]==1) {
                            dayState[6]=0;
                            dayButton[6].setSelected(true);
                        } else {
                            dayState[6]=1;
                            dayButton[6].setSelected(false);
                        }
                        break;
                    case R.id.time_saveBtn:
                        sendShareData();
                        break;


                }
            }
        };

        saveBtn.setOnClickListener(Listener);

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
                    for(int i=0;i<7;i++) {
                        dayState[i]=0;
                        dayButton[i].setSelected(true);
                    }
                }
                else{
                    for(int i=0;i<7;i++) {
                        dayState[i]=1;
                        dayButton[i].setSelected(false);
                    }
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
