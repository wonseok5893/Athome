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
import android.widget.Toast;

import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.main.MainActivity;
import com.example.athome.reservation.CustomTimePickerDialog;
import com.example.athome.R;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.ShareInfoResult;
import com.example.athome.retrofit.sendShareResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;

public class SharedParkingTime extends AppCompatActivity {

    // 처음에 초기값 받아올 부분
    private ShareInfoResult sir;
    private sendShareResult sendRes;
    // 시작시간, 종료시간, 뒤로가기 버튼 , 전부 제어하는 스위치, 저장버튼
    private TextView startTime, endTime;
    private Button backBtn;
    private Switch allBtn;
    private ImageButton saveBtn;

    // 요일별 버튼들과 요일별 버튼의 값을 저장하는 부분
    private Button[] dayButton = new Button[7];
    private int[] dayState = new int[7];

    static final int START_TIME_DIALOG_ID = 1;
    static final int END_TIME_DIALOG_ID = 2;

    // 시작시간, 종료시간
    private int sHour;
    private int sMinute;
    private int eHour;
    private int eMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_time);


        Log.d("junggyu", "오늘의 요일값(일,월,화,수,목,금,토) -> 0,1,2,3,4,5,6 : " + doDayOfWeek() + "");

        this.Initialize();
        initShareData();
        setButtonState();
        this.setListner();

    }

    // 해당 화면으로 전환될때 서버에서 시작, 종료시간과 요일별 데이터를 받아옴
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
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (sir.getResult().equals("success")) {

            Toast.makeText(getApplicationContext(), sir.getMessage(), Toast.LENGTH_SHORT).show();
            String sSplit[] = sir.getStartTime().split(":");

            sHour = Integer.parseInt(sSplit[0]);
            sMinute = Integer.parseInt(sSplit[1]);
            String eSplit[] = sir.getEndTime().split(":");
            eHour = Integer.parseInt(eSplit[0]);
            eMinute = Integer.parseInt(eSplit[1]);

            startTime.setText(sir.getStartTime());
            endTime.setText(sir.getEndTime());
            dayState[0] = sir.getSun();
            dayState[1] = sir.getMon();
            dayState[2] = sir.getTue();
            dayState[3] = sir.getWed();
            dayState[4] = sir.getThu();
            dayState[5] = sir.getFri();
            dayState[6] = sir.getSat();

        } else {
            Toast.makeText(getApplicationContext(), sir.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /*
        for(int i=0;i<7;i++) {
            if(i==doDayOfWeek()) {
                MainActivity.todayFlag = dayState[i];
                if(MainActivity.todayFlag==1) {
                    MainActivity.shareOn.setChecked(true);
                } else {
                    MainActivity.shareOff.setChecked(true);
                }
            }
        }
        */
        if (sir.getResult().equals("success"))
            setTodayFlag();


    }

    void sendShareData() {
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기

        String days = Arrays.toString(dayState);

        ApiService serviceApi = new RestRequestHelper().getApiService();
        Call<sendShareResult> res = serviceApi.sendShareData(sharedToken, days, startTime.getText().toString(), endTime.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendRes = res.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (sendRes != null) {
            if (sendRes.getResult().equals("success")) {
                Toast.makeText(this, sendRes.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, sendRes.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
        }
        setTodayFlag();
        Toast.makeText(getApplicationContext(), "저장에 성공했습니다.", Toast.LENGTH_SHORT).show();
    }

    public void Initialize() {
        startTime = findViewById(R.id.share_time_start);
        endTime = findViewById(R.id.share_time_end);
        startTime.setText("00:00");
        endTime.setText("00:00");
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

    void setButtonState() {

        int count = 0;

        while (true) {
            if (dayState[count] == 0) {
                dayButton[count].setSelected(true);
            } else {
                dayButton[count].setSelected(false);
            }

            count++;
            if (count == 7)
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
                        if (dayState[0] == 1) {
                            dayState[0] = 0;
                            dayButton[0].setSelected(true);
                        } else {
                            dayState[0] = 1;
                            dayButton[0].setSelected(false);
                        }
                        break;
                    case R.id.monday:
                        if (dayState[1] == 1) {
                            dayState[1] = 0;
                            dayButton[1].setSelected(true);
                        } else {
                            dayState[1] = 1;
                            dayButton[1].setSelected(false);
                        }
                        break;
                    case R.id.tuesday:
                        if (dayState[2] == 1) {
                            dayState[2] = 0;
                            dayButton[2].setSelected(true);
                        } else {
                            dayState[2] = 1;
                            dayButton[2].setSelected(false);
                        }
                        break;
                    case R.id.wensday:
                        if (dayState[3] == 1) {
                            dayState[3] = 0;
                            dayButton[3].setSelected(true);
                        } else {
                            dayState[3] = 1;
                            dayButton[3].setSelected(false);
                        }
                        break;
                    case R.id.thursday:
                        if (dayState[4] == 1) {
                            dayState[4] = 0;
                            dayButton[4].setSelected(true);
                        } else {
                            dayState[4] = 1;
                            dayButton[4].setSelected(false);
                        }
                        break;
                    case R.id.friday:
                        if (dayState[5] == 1) {
                            dayState[5] = 0;
                            dayButton[5].setSelected(true);
                        } else {
                            dayState[5] = 1;
                            dayButton[5].setSelected(false);
                        }
                        break;
                    case R.id.saturday:
                        if (dayState[6] == 1) {
                            dayState[6] = 0;
                            dayButton[6].setSelected(true);
                        } else {
                            dayState[6] = 1;
                            dayButton[6].setSelected(false);
                        }
                        break;
                    case R.id.time_saveBtn:
                        if (eHour < sHour) {
                            startTime.setText("00:00");
                            endTime.setText("00:00");
                            Toast.makeText(getApplicationContext(), "올바른 시간을 선택해 주세요!", Toast.LENGTH_LONG).show();
                        } else if (eHour == sHour && eMinute <= sMinute) {
                            startTime.setText("00:00");
                            endTime.setText("00:00");
                            Toast.makeText(getApplicationContext(), "올바른 시간을 선택해 주세요!", Toast.LENGTH_LONG).show();
                        } else {
                            sendShareData();
                        }
                        break;


                }
            }
        };

        saveBtn.setOnClickListener(Listener);

        backBtn.setOnClickListener(Listener);
        startTime.setOnClickListener(Listener);
        endTime.setOnClickListener(Listener);

        for (int i = 0; i < 7; i++) {
            dayButton[i].setOnClickListener(Listener);
        }

        allBtn.setChecked(true);
        allBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < 7; i++) {
                        dayState[i] = 1;
                        dayButton[i].setSelected(false);
                    }
                } else {
                    for (int i = 0; i < 7; i++) {
                        dayState[i] = 0;
                        dayButton[i].setSelected(true);
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
                    sHour = hourOfDay;
                    sMinute = minute;

                    if ((sHour / 10) == 0) {
                        if (sMinute == 0) {
                            startTime.setText(String.format("%s:%s", "0" + sHour, "00"));
                        } else {
                            startTime.setText(String.format("%s:%s", "0" + sHour, sMinute));
                        }
                    } else {
                        if (sMinute == 0) {
                            startTime.setText(String.format("%s:%s", sHour, "00"));
                        } else {
                            startTime.setText(String.format("%s:%s", sHour, sMinute));

                        }


                    }

                }
            };

    //EndTimePicker 리스너
    private CustomTimePickerDialog.OnTimeSetListener mEndTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    eHour = hourOfDay;
                    eMinute = minute;


                    if ((eHour / 10) == 0) {
                        if (eMinute == 0) {
                            endTime.setText(String.format("%s:%s", "0" + eHour, "00"));
                        } else {
                            endTime.setText(String.format("%s:%s", "0" + eHour, eMinute));
                        }
                    } else {
                        if (eMinute == 0) {
                            endTime.setText(String.format("%s:%s", eHour, "00"));
                        } else {
                            endTime.setText(String.format("%s:%s", eHour, eMinute));

                        }
                    }
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_TIME_DIALOG_ID:
                return new CustomTimePickerDialog(this, mStartTimeSetListener, sHour, sMinute, true);

            case END_TIME_DIALOG_ID:
                return new CustomTimePickerDialog(this, mEndTimeSetListener, eHour, eMinute, true);
        }

        return null;
    }

    private int doDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        int result = 0;

        int nWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (nWeek == 1) {
            result = 0;
        } else if (nWeek == 2) {
            result = 1;
        } else if (nWeek == 3) {
            result = 2;
        } else if (nWeek == 4) {
            result = 3;
        } else if (nWeek == 5) {
            result = 4;
        } else if (nWeek == 6) {
            result = 5;
        } else if (nWeek == 7) {
            result = 6;
        }

        return result;
    }

    void setTodayFlag() {
        for (int i = 0; i < 7; i++) {
            if (i == doDayOfWeek()) {
                MainActivity.todayFlag = dayState[i];
                if (MainActivity.todayFlag == 1) {
                    MainActivity.shareOn.setChecked(true);
                } else {
                    MainActivity.shareOff.setChecked(true);
                }
            }
        }

    }
}
