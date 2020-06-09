package com.example.athome.reservation_list;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.athome.R;

import java.util.ArrayList;

public class NowReservTicket extends Activity {
    private Button btn_back_reserv_ticket;
    private Button btn_reserv_ticket_payment_cancel; //결제취소
    private Button btn_reserv_ticket_time_extension; //시간연장
    private LinearLayout more_parking_linear; //주차장정보
    final int[] selectdItem={0};
    private TextView now_reserv_start_date,now_reserv_start_time,now_reserv_end_date,now_reserv_end_time,parking_number,now_reserv_car_number,now_reserv_state_value;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.now_reserv_parking_ticket);

        this.InitializeView();
        this.SetListner();
    }

    public void InitializeView(){
        btn_back_reserv_ticket=(Button)findViewById(R.id.btn_back_reserv_ticket);
        btn_reserv_ticket_payment_cancel=(Button)findViewById(R.id. btn_reserv_ticket_payment_cancel);
        btn_reserv_ticket_time_extension=(Button)findViewById(R.id.btn_reserv_ticket_time_extension);
        more_parking_linear=(LinearLayout)findViewById(R.id.more_parking_linear);

        Intent intent = getIntent();
        now_reserv_start_date = (TextView)findViewById(R.id.now_reserv_start_date);
        now_reserv_start_time = (TextView)findViewById(R.id.now_reserv_start_time);
        now_reserv_end_date = (TextView)findViewById(R.id.now_reserv_end_date);
        now_reserv_end_time = (TextView)findViewById(R.id.now_reserv_end_time);
        parking_number = (TextView)findViewById(R.id.parking_number);
        now_reserv_car_number = (TextView)findViewById(R.id.now_reserv_car_number);
        now_reserv_state_value = (TextView)findViewById(R.id.now_reserv_state_value);

        now_reserv_start_date.setText(intent.getStringExtra("nowReserveStartDate"));
        now_reserv_start_time.setText(intent.getStringExtra("nowReserveStartTime"));
        now_reserv_end_date.setText(intent.getStringExtra("nowReserveEndDate"));
        now_reserv_end_time.setText(intent.getStringExtra("nowReserveEndTime"));
        parking_number.setText(intent.getStringExtra("nowReserveCarNumber"));
        now_reserv_car_number.setText(intent.getStringExtra("nowReserveParkingNumber"));
        now_reserv_state_value.setText(intent.getStringExtra("nowReserveState"));
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_reserv_ticket: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.btn_reserv_ticket_payment_cancel: //결제취소버튼
                        /*종료시간 몇 분전 설정해놓고 그 시간전에 결제 취소를 누르면 결제 취소가능하고
                          결제취소 시간이 지나면 결제취소 불가*/
                        AlertDialog.Builder dialog1=new AlertDialog.Builder(NowReservTicket.this);
                        dialog1.setTitle("결제를 취소하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(NowReservTicket.this
                                                ,"취소"
                                                ,Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog1.create();
                        dialog1.show();
                        break;

                    case R.id.btn_reserv_ticket_time_extension: //시간연장버튼
                        /*만약 연장시간(ex:1시간)선택했을때 연장시간 안에 다른 예약시간과 겹치면 연장불가띄어주고
                         연장이 가능하다면 연장시간만큼 추가 결제해야함 */

                        //시간연장버튼 눌렀을 때 연장할 수 있는 다이얼로그
                        final String[] items= new String[]{"10분","30분","1시간"};
                        AlertDialog.Builder dialog2=new AlertDialog.Builder(NowReservTicket.this);
                        dialog2.setTitle("시간 연장")
                                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectdItem[0]=which;
                                    }
                                })
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(NowReservTicket.this
                                                ,"취소"
                                                ,Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog2.create();
                        dialog2.show();
                        break;
                    case R.id.more_parking_linear://해당 주차장정보로 넘어가기
                        break;

                }
            }
        };
        btn_back_reserv_ticket.setOnClickListener(Listener);
        btn_reserv_ticket_payment_cancel.setOnClickListener(Listener);
        btn_reserv_ticket_time_extension.setOnClickListener(Listener);
        more_parking_linear.setOnClickListener(Listener);
    }
}
