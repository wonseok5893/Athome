package com.example.athome.reservation;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.main.MainActivity;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.ReserveListResult;
import com.example.athome.retrofit.sendReserveResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;

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
    private TextView non_parking_time_result;
    private TextView reserv_payment_amount_value_non;
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

    static final int START_DATE_DIALOG_ID = 0;
    static final int END_DATE_DIALOG_ID = 1;
    static final int START_TIME_DIALOG_ID = 2;
    static final int END_TIME_DIALOG_ID = 3;
    private sendReserveResult sendResult;

    private ReserveListResult reserveResult;
    private ArrayList<String> startTimeList = new ArrayList<>();
    private ArrayList<String> endTimeList = new ArrayList<>();

    private int[][] todayReserve = new int[25][6];
    private int[][] tomorrowReserve = new int[25][6];

    private int[] locationDaySet;
    private String locationStartTime;
    private String locationEndTime;

    private ArrayList<View> ViewList = new ArrayList<>(); //예약 시간 나타나는 창
    private long calDate;
    private int pay;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.non_member_reserve);

        this.InitializeView();
        this.SetListner();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("jiwon", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("jiwon", msg);
                    }
                });

        Intent intent = getIntent();
        _id = intent.getStringExtra("locationId");
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        parking_number.setText(intent.getStringExtra("parkingInfo"));

        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        sharedToken = sf.getString("token", "");
        locationDaySet = intent.getIntArrayExtra("locationDaySet");
        locationStartTime = intent.getStringExtra("locationStartTime");
        locationEndTime = intent.getStringExtra("locationEndTime");

        ReservationList(intent); //마커의 예약 정보 받아오기
        parseMsg(); // 2차원 배열에 예약 정보 초기화
        makeTimeTable(locationStartTime, locationEndTime); //144개 뷰 예약 허용 시간 외 gray 예약 시간 red 로 표현

        reserv_end_time_select.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    timeCalc();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void InitializeView() {
        reserv_start = findViewById(R.id.non_reserv_start);
        reserv_end = findViewById(R.id.non_reserv_end);

        btn_back_reserv = findViewById(R.id.btn_back_non_reserv);
        parking_number = findViewById(R.id.non_parking_number);
        reserv_start_date_select = findViewById(R.id.non_reserv_start_date_select);
        reserv_end_date_select = findViewById(R.id.non_reserv_end_date_select);

        reserv_start_time_select = findViewById(R.id.non_reserv_start_time_select);
        reserv_end_time_select = findViewById(R.id.non_reserv_end_time_select);

        non_car_id = findViewById(R.id.non_car_id);
        non_name_id = findViewById(R.id.non_name_id);
        non_phnum_id = findViewById(R.id.non_phnum_id);
        btn_next_reserv = findViewById(R.id.btn_next_non_reserv);
        non_parking_time_result = findViewById(R.id.non_parking_time_result);
        reserv_payment_amount_value_non = findViewById(R.id.reserv_payment_amount_value_non);

        for (int idx_loop = 1; idx_loop < 145; idx_loop++) {
            int viewId = getResources().getIdentifier("view" + idx_loop, "id", getPackageName());
            View viewN = findViewById(viewId);
            ViewList.add(viewN);
        }

        //현재 날짜와 시간 가져오기
        c = Calendar.getInstance();
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

    public void SetListner() {
        View.OnClickListener Listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_back_reserv: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
                        break;
                    case R.id.non_reserv_start://예약시작날짜와시간설정
                        pay = 0;
                        showDialog(START_DATE_DIALOG_ID);
                        break;
                    case R.id.non_reserv_end: //예약종료날짜와시간설정
                        showDialog(END_DATE_DIALOG_ID);
                        break;

                    case R.id.btn_next_non_reserv:
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String startTimeString = smYear + "-" + (smMonth + 1) + "-" + smDay + " " + smHour + ":" + smMinute;//"2013-04-08 10:10";
                        String endTimeString = emYear + "-" + (emMonth + 1) + "-" + emDay + " " + emHour + ":" + emMinute;//"2013-04-08 10:10";
                        try {
                            startTime = transFormat.parse(startTimeString);
                            endTime = transFormat.parse(endTimeString);
                            Log.d("time", startTime.toString());
                            Log.d("time", endTime.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String nonCarnum = non_car_id.getText().toString();
                        String nonName = non_name_id.getText().toString();
                        String nonPhnum = non_phnum_id.getText().toString();
                        if (reserv_end_time_select.getText().toString().equals("0") || reserv_end_time_select.getText().toString().equals("올바르지 않은 시간 설정")) {
                            Log.i("jiwon", "0 혹은 올바르지 않은 시간 설정");
                            Toast.makeText(nonReserveActivity.this, "시간 설정을 다시 해주십시오.", Toast.LENGTH_SHORT).show();
                        } else if (nonCarnum.equals("") || nonName.equals("") || nonPhnum.equals("")) {
                            Log.i("jiwon", "입력값이 없다.");
                            Toast.makeText(nonReserveActivity.this, "인적 정보를 입력해주십시오.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("jiwon", "전부 okay");
                            ApiService apiService = new RestRequestHelper().getApiService();
                            final Call<sendReserveResult> res = apiService.sendReserveNonUser(sharedToken, _id, nonCarnum, nonPhnum, nonName, startTime, endTime, pay,token);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        sendResult = res.execute().body();
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

                            if (sendResult != null) {
                                if (sendResult.getResult().equals("success")) {
                                    Toast.makeText(nonReserveActivity.this, sendResult.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(nonReserveActivity.this, ReserveConfirm.class);
                                    startActivity(intent);
                                } else {
                                    reserv_start_date_select.setText("00-00-00");
                                    reserv_end_date_select.setText("00-00-00");
                                    reserv_start_time_select.setText("00:00");
                                    reserv_end_time_select.setText("00:00");
                                    Toast.makeText(nonReserveActivity.this, sendResult.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                reserv_start_date_select.setText("00-00-00");
                                reserv_end_date_select.setText("00-00-00");
                                reserv_start_time_select.setText("00:00");
                                reserv_end_time_select.setText("00:00");
                                Toast.makeText(nonReserveActivity.this, "다시 시도해주십시오.", Toast.LENGTH_SHORT);
                            }
                        }
                        break;
                }
            }
        };
        btn_back_reserv.setOnClickListener(Listener);
        reserv_start.setOnClickListener(Listener);
        reserv_end.setOnClickListener(Listener);
        btn_next_reserv.setOnClickListener(Listener);
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
                    reserv_start_date_select.setText(String.format("%d-%d-%d", smYear, smMonth + 1, smDay));
                    reserv_end_date_select.setText(String.format("00-00-00"));
                    reserv_end_time_select.setText(String.format("00:00"));
                    reserv_payment_amount_value_non.setText("0");
                    non_parking_time_result.setText("0");
                }
            };

    //EndDatePicker 리스너
    private DatePickerDialog.OnDateSetListener mEndDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    emYear = year;
                    emMonth = monthOfYear;
                    emDay = dayOfMonth;
                    showDialog(END_TIME_DIALOG_ID);

                    reserv_end_date_select.setText(String.format("%d-%d-%d", emYear, emMonth + 1, emDay));

                }
            };

    //StartTimePicker 리스너
    private CustomTimePickerDialog.OnTimeSetListener mStartTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    smHour = hourOfDay;
                    smMinute = minute;
                    reserv_start_time_select.setText(String.format("%d시 %d분", smHour, smMinute));
                }
            };

    //EndTimePicker 리스너
    private CustomTimePickerDialog.OnTimeSetListener mEndTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    emHour = hourOfDay;
                    emMinute = minute;
                    reserv_end_time_select.setText(String.format("%d시 %d분", emHour, emMinute));
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar cal = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        minDate.set(year, month, date);

        cal.add(Calendar.DATE, 1);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DATE);
        maxDate.set(year, month, date);

        switch (id) {
            case START_DATE_DIALOG_ID:
                DatePickerDialog startDate = new DatePickerDialog(this, mStartDateSetListener, smYear, smMonth, smDay);
                startDate.getDatePicker().setMinDate(minDate.getTime().getTime());
                startDate.getDatePicker().setMaxDate(maxDate.getTime().getTime());
                return startDate;

            case END_DATE_DIALOG_ID:
                DatePickerDialog endDate = new DatePickerDialog(this, mEndDateSetListener, emYear, emMonth, emDay);
                minDate.set(smYear, smMonth, smDay);
                endDate.getDatePicker().setMinDate(minDate.getTime().getTime());
                endDate.getDatePicker().setMaxDate(maxDate.getTime().getTime());
                return endDate;

            case START_TIME_DIALOG_ID:
                return new CustomTimePickerDialog(this, mStartTimeSetListener, smHour, smMinute, true);

            case END_TIME_DIALOG_ID:
                return new CustomTimePickerDialog(this, mEndTimeSetListener, emHour, emMinute, true);
        }
        return null;
    }

    private void ReservationList(Intent intent) {


        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<ReserveListResult> res = serviceApi.getReserveData(intent.getStringExtra("locationId"));
        Log.i("jiwon", "locationId : " + intent.getStringExtra("locationId"));
        Log.d("ResTest", intent.getStringExtra("locationId"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reserveResult = res.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (reserveResult == null) {
            Log.i("jiwon", "예약사항없음 or 실패 으앙!");
        } else {
            int reservCount = reserveResult.getReservationList().size();
            Log.i("jiwon", "reserveCount" + Integer.toString(reservCount));
            for (int i = 0; i < reservCount; i++) {
                String s = reserveResult.getReservationList().get(i).getStartTime();
                String e = reserveResult.getReservationList().get(i).getEndTime();
                startTimeList.add(s);
                endTimeList.add(e);
                Log.i("jiwon", s);
                Log.i("jiwon", e);
            }
        }
    }

    private void parseMsg() {
        for (int i = 0; i < startTimeList.size(); i++) {

            // String -> date
            String StartStr = "";
            StartStr = startTimeList.get(i);
            String a = StartStr.substring(0, 20);
            String b = StartStr.substring(30);
            StartStr = a + b;

            String EndStr = "";
            EndStr = endTimeList.get(i);
            a = EndStr.substring(0, 20);
            b = EndStr.substring(30);
            EndStr = a + b;


            SimpleDateFormat sdfToDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
            Date StartDate = null;
            Date EndDate = null;
            try {
                //date -> String
                StartDate = sdfToDate.parse(StartStr);
                EndDate = sdfToDate.parse(EndStr);

                SimpleDateFormat sdfToReserve = new SimpleDateFormat("MM dd HH mm", Locale.US);
                StartStr = sdfToReserve.format(StartDate);
                EndStr = sdfToReserve.format(EndDate);
                String[] StartSplit = StartStr.split(" ");
                String[] EndSplit = EndStr.split(" ");

                int sDay = Integer.parseInt(StartSplit[1]);
                int sHour = Integer.parseInt(StartSplit[2]);
                int sMin = Integer.parseInt(StartSplit[3]) / 10;
                int eDay = Integer.parseInt(EndSplit[1]);
                int eHour = Integer.parseInt(EndSplit[2]);
                int eMin = Integer.parseInt(EndSplit[3]) / 10;
                Log.i("jiwon", "" + sDay + " " + sHour + " " + sMin + " " + "///" + eDay + " " + eHour + " " + eMin);

                //예약 타입 테이블 채우는 함수
                setReserveList(sDay, sHour, sMin, eDay, eHour, eMin);

                Log.d("junggyu", startTimeList.get(i));
                Log.d("junggyu", endTimeList.get(i));
                Log.d("junggyu", StartStr);
                Log.d("junggyu", StartSplit[0]);
                Log.d("junggyu", StartSplit[1]);
                Log.d("junggyu", StartSplit[2]);
                Log.d("junggyu", StartSplit[3]);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < 25; i++) {
            Log.i("jiwon", "[" + i + "]" + Arrays.toString(todayReserve[i]));
        }


    }

    private void setReserveList(int sDay, int sHour, int sMin, int eDay, int eHour, int eMin) {
        if (sDay == eDay) {
            Log.i("jiwon", "if (sDay == eDay) ");
            if (sDay == todayReserve[24][1]) {
                Log.i("jiwon", "(sDay == todayReserve[24][1])");
                for (int j = sHour; j <= eHour; j++) {  // 0 ~ 23 입력 받을건데
                    int k = 0;
                    int last = 6;
                    if (j == sHour)
                        k = sMin;
                    if (j == eHour)
                        last = eMin;
                    for (; k < last; k++) {
                        todayReserve[j][k] = 1;
                    }
                }
            } else if (sDay == tomorrowReserve[24][1]) {
                Log.i("jiwon", "(sDay == tomorrowReserve[24][1])");
                for (int j = sHour; j <= eHour; j++) {
                    int k = 0;
                    int last = 6;
                    if (j == sHour)
                        k = sMin;
                    if (j == eHour)
                        last = eMin;
                    for (; k < last; k++) {
                        tomorrowReserve[j][k] = 1;
                    }
                }
            }
        } else {
            if (sDay == todayReserve[24][1]) {
                for (int j = sHour; j <= 24; j++) {
                    int k = 0;
                    if (j == sHour)
                        k = sMin;
                    for (; k < 6; k++) {
                        todayReserve[j][k] = 1;
                    }
                }
                for (int j = 0; j <= eHour; j++) {
                    int last = 6;
                    if (j == eHour)
                        last = eMin;
                    for (int k = 0; k < last; k++) {
                        tomorrowReserve[j][k] = 1;
                    }
                }
            }
        }
    }

    private void makeTimeTable(String locationStartTime, String locationEndTime) {
        if (locationDaySet[todayReserve[24][2]] == 1) {
            String[] start = locationStartTime.split(":");
            String[] end = locationEndTime.split(":");

            int startH = Integer.parseInt(start[0]);
            int startM = Integer.parseInt(start[1])/10;
            int endH = Integer.parseInt(end[0]);
            int endM = Integer.parseInt(end[1])/10;

            int count = 0;
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 6; j++) {
                    if (i == startH) {
                        if (j < startM) {
                            ViewList.get(count).setBackgroundColor(Color.GRAY);
                        }
                    } else if (i == endH) {
                        if (j + 1 > endM) {
                            ViewList.get(count).setBackgroundColor(Color.GRAY);
                        }
                    } else if (i < startH || i > endH) {
                        ViewList.get(count).setBackgroundColor(Color.GRAY);
                    }
                    count++;
                }
            }

            count = 0;
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 6; j++) {
                    if (todayReserve[i][j] == 1)
                        ViewList.get(count).setBackgroundColor(Color.RED);
                    count++;
                }
            }
        } else {
            for (int i = 0; i < 144; i++) {
                ViewList.get(i).setBackgroundColor(Color.GRAY);
            }
        }
    }

    void timeCalc() throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String startTimeString = smYear + "-" + (smMonth + 1) + "-" + smDay + " " + smHour + ":" + smMinute;//"2013-04-08 10:10";
        String endTimeString = emYear + "-" + (emMonth + 1) + "-" + emDay + " " + emHour + ":" + emMinute;//"2013-04-08 10:10";

        int resultMin;
        int resultHour;
        int resultDay;

        startTime = transFormat.parse(startTimeString);
        endTime = transFormat.parse(endTimeString);
        calDate = endTime.getTime() - startTime.getTime();

        // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
        // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
        if (calDate > 0) {
            resultHour = emHour - smHour;  // 1
            resultMin = emMinute - smMinute; // -50
            resultDay = emDay - smDay;
            if (resultMin < 0) {

                resultMin += 60; // 10
                resultHour -= 1; // 0
            }
            if (resultDay == 1) {
                resultHour += 24;
            }
            non_parking_time_result.setText(resultHour + "시간" + resultMin + "분");
            pay = resultHour * 600 + resultMin / 10 * 100;
            reserv_payment_amount_value_non.setText(Integer.toString(pay));

        } else {
            non_parking_time_result.setText("올바르지 않은 시간 설정");
            reserv_payment_amount_value_non.setText("0");
        }

        return;
    }
}
