package com.example.athome;

import android.content.Intent;
import android.os.Build;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserveActivity extends AppCompatActivity {
    private Button nextBtn;
    private DatePicker datePicker;
    private TimePicker startTime;
    private TimePicker endTime;
    private EditText en_phnum;
    private TextView or_phnum;
    private EditText en_carnum;
    private TextView or_carnum;



    private int month=0;
    private int year=0;
    private int day=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        nextBtn = (Button)findViewById(R.id.res_next);
        datePicker = (DatePicker)findViewById(R.id.picker_date);
        startTime = (TimePicker)findViewById(R.id.picker_start);
        endTime = (TimePicker)findViewById(R.id.picker_end);
        or_phnum=(TextView)findViewById(R.id.origin_phnum); //기존 회원 전화번호를 찾아서 출력해야함.
        en_phnum = (EditText)findViewById(R.id.new_num);
        en_carnum = (EditText)findViewById(R.id.new_num); //기존 차량 정보를 찾아서 출력해야함.
        or_carnum = (TextView)findViewById(R.id.origin_num);
        //편의상 ap,pm 없애고 24시간으로 설정
        startTime.setIs24HourView(true);
        endTime.setIs24HourView(true);




        nextBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String enPhone = en_phnum.getText().toString();
                String orPhone = or_phnum.getText().toString();
                String enCar = en_carnum.getText().toString();
                String orCar = or_carnum.getText().toString();

                if(enPhone.isEmpty()&&orPhone.isEmpty()){
                    Toast.makeText(getApplicationContext(), "모든 항목을 기입하십시오.", Toast.LENGTH_SHORT).show();
                }else if (enCar.isEmpty()&&orCar.isEmpty()){Toast.makeText(getApplicationContext(), "모든 항목을 기입하십시오.", Toast.LENGTH_SHORT).show();
                }else {
                    //서버에 밀리세컨드로 예약 시작 시간, 종료 시간 전송
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, startTime.getHour());
                    cal.set(Calendar.MINUTE, startTime.getMinute());
                    cal.set(Calendar.DAY_OF_MONTH, day);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.YEAR, year);

                    long stime = cal.getTimeInMillis();
                    cal.set(Calendar.HOUR_OF_DAY, endTime.getHour());
                    cal.set(Calendar.MINUTE, endTime.getMinute());
                    long etime = cal.getTimeInMillis();
                    //date, time -> ms 끝

                    //서버와 http 통신 하는 부분
                    ApiService serviceApi = new RestRequestHelper().getApiService();
                    Call<ResponseBody> call = serviceApi.sendReserve(orCar,orPhone,stime,etime);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful() && response.body() !=null) {
                                Toast.makeText(ReserveActivity.this, "저장성공했습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ReserveActivity.this, "실패.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(ReserveActivity.this,"저장실패.",Toast.LENGTH_SHORT).show();
                        }
                    });
                    //통신 끝 + 오류 수정해야 할 것 : 전송속도가 느리면 다음 코드 실행되서 화면이 넘어감. sleep 쓰면 될 듯
                    Intent intent = new Intent(getApplicationContext(),ReserveConfirm.class);
                    startActivity(intent);
                }



            }
        });

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int sel_year, int monthOfYear, int dayOfMonth) {
                year = sel_year;
                month = monthOfYear;
                day = dayOfMonth; // 예약날짜 리턴
            }
        });

    }


}
