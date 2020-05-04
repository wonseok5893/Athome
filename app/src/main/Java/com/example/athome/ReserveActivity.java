package com.example.athome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

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




        nextBtn.setOnClickListener(new View.OnClickListener() {
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
