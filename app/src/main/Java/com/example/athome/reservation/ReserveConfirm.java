package com.example.athome.reservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athome.R;
import com.example.athome.User;
import com.example.athome.main.MainActivity;


public class ReserveConfirm extends AppCompatActivity {
    private Button cancelBtn;
    private Button naviBtn;
    private Button closeBtn;
    private TextView location,date,phnum,carnum,fee,payment,parking_num;
    private User user = MainActivity.getUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_confirm);
        Initialize();
        Intent intent = getIntent();


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //바로안내 버튼-> 경로 안내시작코드 수행해야함.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

       cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ReserveConfirm.this);
                dialog.setTitle("예약취소 확인")
                        .setMessage("예약을 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(ReserveConfirm.this,"예약이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), ReserveConfirm.class);
                                startActivity(intent);
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });

       location.setText(intent.getStringExtra("locationName"));
       date.setText(intent.getStringExtra("reservDate"));
       if(user.getUserId()==null) {
           phnum.setText(intent.getStringExtra("phnum"));
       } else {
           phnum.setText(user.getUserPhone());
       }
       carnum.setText(intent.getStringExtra("carNum"));
       fee.setText(intent.getStringExtra("payMoney"));

    }

    public void Initialize(){
        naviBtn = (Button)findViewById(R.id.con_naviBtn);
        cancelBtn = (Button)findViewById(R.id.con_cancelBtn);
        closeBtn=(Button)findViewById(R.id.btn_back_reserv_close);
        location = (TextView)findViewById(R.id.con_location); //예약장소 주소
        date = (TextView)findViewById(R.id.con_date); //예약날짜
        phnum= (TextView)findViewById(R.id.con_phnum); //전화번호
        carnum= (TextView)findViewById(R.id.con_carnum); //차량번호
        fee= (TextView)findViewById(R.id.con_fee); //결제요금
        payment= (TextView)findViewById(R.id.con_payment); //결제방법
        parking_num = (TextView)findViewById(R.id.con_parkingnum); //주차구역번호

    };
}
