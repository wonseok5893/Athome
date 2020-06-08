package com.example.athome.reservation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
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

public class ReserveActivity extends AppCompatActivity {
    private LinearLayout reserv_start;
    private LinearLayout reserv_end;
    private Button btn_back_reserv;
    private Button btn_next_reserv;
    private TextView parking_number;
    private TextView reserv_start_date_select;
    private TextView reserv_end_date_select;
    private TextView reserv_start_time_select;
    private TextView reserv_end_time_select;
    private TextView parking_car_number_select;
    private TextView parking_time_result;
    private TextView all_point_use; //포인트전액사용하기
    private CarListAdapter adapter;
    private ArrayList<ItemAccountCarData> data=new ArrayList<>();

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
        setContentView(R.layout.activity_reserve);

        this.InitializeView();
        this.SetListner();
        Intent intent = getIntent();
        _id = intent.getStringExtra("locationId");
        userId = intent.getStringExtra("userId");
        latitude = intent.getDoubleExtra("latitude",0);
        longitude = intent.getDoubleExtra("longitude",0);
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        sharedToken = sf.getString("token", "");

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



    public void InitializeView(){
        reserv_start=findViewById(R.id.reserv_start);
        reserv_end=findViewById(R.id.reserv_end);

        btn_back_reserv=findViewById(R.id.btn_back_reserv);
        parking_number=findViewById(R.id.parking_number);
        reserv_start_date_select=findViewById(R.id.reserv_start_date_select);
        reserv_end_date_select=findViewById(R.id.reserv_end_date_select);

        reserv_start_time_select=findViewById(R.id.reserv_start_time_select);
        reserv_end_time_select=findViewById(R.id.reserv_end_time_select);

        parking_car_number_select=findViewById(R.id.parking_car_number_select);
        btn_next_reserv=findViewById(R.id.btn_next_reserv);
        parking_car_number_select=findViewById(R.id.parking_car_number_select);
        parking_time_result=findViewById(R.id.parking_time_result);
        all_point_use=findViewById(R.id.all_point_use);
        all_point_use.setPaintFlags(all_point_use.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG); //밑줄긋기

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
                    case R.id.reserv_start://예약시작날짜와시간설정
                        showDialog(START_DATE_DIALOG_ID);
                        break;
                    case R.id.reserv_end: //예약종료날짜와시간설정
                        showDialog(END_DATE_DIALOG_ID);
                        try {
                            timeCalc();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;

                    case R.id.parking_car_number_select://차량번호 선택, 직접입력
                        showDirectInputAlertDialog(ReserveActivity.this);
                        break;
                    case R.id.btn_next_reserv:
                        Log.d("junggyu","버튼이 눌렸습니다.");

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
                        String carNumber = parking_car_number_select.getText().toString();
                        ApiService apiService = new RestRequestHelper().getApiService();
                        Call<ResponseBody> res = apiService.sendReserve(sharedToken, _id, carNumber, startTime,endTime);
                        res.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(ReserveActivity.this,"예약 되셨습니다.",Toast.LENGTH_SHORT).show();
                                Log.d("junggyu","예약 성공");
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(ReserveActivity.this,"다시 시도해주십시오.",Toast.LENGTH_SHORT).show();
                                Log.d("junggyu","다시시도");

                            }
                        });
                        break;
               }
            }
        };
        btn_back_reserv.setOnClickListener(Listener);
        btn_next_reserv.setOnClickListener(Listener);
        reserv_start.setOnClickListener(Listener);
        reserv_end.setOnClickListener(Listener);
        parking_car_number_select.setOnClickListener(Listener);
    }

    private void showDirectInputAlertDialog(Activity activity) {
        //다이얼로그를 정의하기위해 Dialog클래스를 생성
        final Dialog dlg = new Dialog(activity);
        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.reserv_car_select_dialog);
        // 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button btn_reserv_car_select_cancel = (Button) dlg.findViewById(R.id.btn_reserv_car_select_cancel);
        final ListView reserv_car_select_listView=(ListView)dlg.findViewById(R.id.reserv_car_select_listView);
        final TextView reserv_car_direct_input=(TextView)dlg.findViewById(R.id.reserv_car_direct_input);

        btn_reserv_car_select_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });

        reserv_car_direct_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 차량번호 직접입력하는 다이얼로그 생성
                AccountCarRegister accountCarRegister= new AccountCarRegister(ReserveActivity.this);
                // 다이얼로그 호출
                //그외 작업
                accountCarRegister.callFunction();
            }
        });

        adapter=new CarListAdapter(this, R.layout.reserv_car_select_dialog_item, data);
        reserv_car_select_listView.setAdapter(adapter);


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
                    reserv_start_date_select.setText(String.format("%d-%d-%d",smYear,smMonth+1,smDay));

                }
            };

    //StartEndPicker 리스너
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


    int timeCalc() throws ParseException {

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String startTimeString = smYear + "-" + (smMonth + 1) + "-" + smDay + " " + smHour + ":" + smMinute;//"2013-04-08 10:10";
        String endTimeString = emYear + "-" + (emMonth + 1) + "-" + emDay + " " + emHour + ":" + emMinute;//"2013-04-08 10:10";

        int resultMin;
        int resultHour;
        int resultDay;

            startTime = transFormat.parse(startTimeString);
            endTime = transFormat.parse(endTimeString);

            long calDate = endTime.getTime() - startTime.getTime();

            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            if(calDate>0){
                resultHour = emHour-smHour;  // 1
                resultMin = emMinute-smMinute; // -50
                resultDay = emDay - smDay;
                if(resultMin<0){

                    resultMin += 60; // 10
                    resultHour -= 1; // 0
                }
                if(resultDay==1){
                    resultHour += 24;
                }
            } else{
                resultHour = emHour-smHour;
                resultMin = emMinute-smMinute;
            }

        parking_time_result.setText(resultHour+"시"+resultMin+"분");

        Log.d("두 날짜의 날짜 차이: ", resultHour+"시"+resultMin+"분");
        return 0;
    }
}
