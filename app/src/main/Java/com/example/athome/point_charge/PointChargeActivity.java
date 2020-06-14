package com.example.athome.point_charge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.account.AccountCarList;
import com.example.athome.account.AccountCarRegister;
import com.example.athome.account.AccountCardList;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.MarkerResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class PointChargeActivity extends Activity {
    private NumberPicker picker_point;
    private Button btn_back_point_charge, btn_point_charging;
    private TextView payment_method_select, payment_amount_value, point_crruent_value, point_after_charging_value;
    private ArrayList<ItemPaymentCardData> data=new ArrayList<>();
    private PaymentCardListAdapter adapter;
    private int chargeMoney=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_charge);
        final Intent intent = getIntent();
        this.InitializeView(intent);
        this.SetListner();
        this.Execution();

        picker_point.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                switch (newVal) {
                    case 0:
                        chargeMoney = 3000;
                        break;
                    case 1:
                        chargeMoney = 5000;
                        break;
                    case 2:
                        chargeMoney = 10000;
                        break;
                    case 3:
                        chargeMoney = 30000;
                        break;
                    case 4:
                        chargeMoney = 50000;
                        break;
                }
                point_after_charging_value.setText((chargeMoney+intent.getIntExtra("point",0))+"P");
                payment_amount_value.setText(chargeMoney+"P");
            }
        });

    }

    public void InitializeView(Intent intent){
        picker_point=(NumberPicker)findViewById(R.id.picker_point);
        btn_back_point_charge=(Button)findViewById(R.id.btn_back_point_charge);
        payment_method_select=(TextView)findViewById(R.id.payment_method_select);
        point_crruent_value=(TextView)findViewById(R.id.point_crruent_value);
        point_crruent_value.setText(intent.getIntExtra("point",0)+"P");
        point_after_charging_value=(TextView)findViewById(R.id.point_after_charging_value);
        payment_amount_value=(TextView)findViewById(R.id.payment_amount_value);
        btn_point_charging=(Button)findViewById(R.id.btn_point_charging);

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
                    case R.id.btn_point_charging: //결제하기

                        Log.d("junggyu", "결제버튼 눌렀음");

                        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                        String sharedToken = sf.getString("token", "");

                        ApiService serviceApi = new RestRequestHelper().getApiService();
                        final Call<ResponseBody> res = serviceApi.sendChargePoint(sharedToken, chargeMoney);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    res.execute().body();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        break;
                }
            }
        };
        btn_back_point_charge.setOnClickListener(Listener);
        btn_point_charging.setOnClickListener(Listener);
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
       // final TextView point_payment_card_number_delete=(TextView)dlg.findViewById(R.id.point_payment_card_number_delete);

        btn_point_payment_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });

        ItemPaymentCardData data1=new ItemPaymentCardData("신한은행",1234,5678, 3333,4444);
        data.add(data1);

        adapter=new PaymentCardListAdapter(this, R.layout.point_payment_dialog_item, data);
        point_payment_listView.setAdapter(adapter);




}



}