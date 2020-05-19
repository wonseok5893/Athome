package com.example.athome.point_charge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.account.AccountCarList;
import com.example.athome.account.AccountCarRegister;

import java.util.ArrayList;
import java.util.List;

public class PointChargeActivity extends Activity {
    private NumberPicker picker_point;
    private Button btn_back_point_charge;
    private TextView payment_method_select;
    private ArrayList<ItemPaymentCardData> data=new ArrayList<>();
    private PaymentCardListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_charge);

        this.InitializeView();
        this.SetListner();
        this.Execution();

    }

    public void InitializeView(){
        picker_point=(NumberPicker)findViewById(R.id.picker_point);
        btn_back_point_charge=(Button)findViewById(R.id.btn_back_point_charge);
        payment_method_select=(TextView)findViewById(R.id.payment_method_select);

    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_point_charge: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.payment_method_select:
                            showAlertDialog(PointChargeActivity.this);
                        break;
                }
            }
        };
        btn_back_point_charge.setOnClickListener(Listener);
        payment_method_select.setOnClickListener(Listener);
    }

    public void Execution(){

        picker_point.setMinValue(0);
        picker_point.setMaxValue(4);
        picker_point.setWrapSelectorWheel(false);
        picker_point.setDisplayedValues(new String[]{"3,000P","5,000P","10,000P","30,000P","50,000P"});

    }

    public void showAlertDialog(Activity activity){

        //다이얼로그를 정의하기위해 Dialog클래스를 생성
        final Dialog dlg = new Dialog(activity);
        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.point_payment_dialog);
        // 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button btn_point_payment_cancel = (Button) dlg.findViewById(R.id.btn_point_payment_cancel);
        final ListView point_payment_listView=(ListView)dlg.findViewById(R.id.point_payment_listView);

        btn_point_payment_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });

        ItemPaymentCardData data1=new ItemPaymentCardData("신한은행",1234,5678);
        data.add(data1);


        adapter=new PaymentCardListAdapter(this, R.layout.point_payment_dialog_item, data);
        point_payment_listView.setAdapter(adapter);
    }

}