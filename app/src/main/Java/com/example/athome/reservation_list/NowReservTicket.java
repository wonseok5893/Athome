package com.example.athome.reservation_list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.athome.R;

import java.util.ArrayList;

public class NowReservTicket extends Activity {
    private Button btn_back_reserv_ticket;
    private Button btn_reserv_ticket_payment_cancel; //결제취소
    private Button btn_reserv_ticket_time_extension; //시간연장
    private LinearLayout more_parking_linear; //주차장정보

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
                        break;
                    case R.id.btn_reserv_ticket_time_extension: //시간연장버튼
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
