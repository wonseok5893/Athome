package com.example.athome.account;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athome.R;

public class AccountCardRegister extends Activity {
    private Context context;

    public AccountCardRegister(Context context){
        this.context=context;
    }

    //호출할 다이얼로그 함수를 정의
    public void callFunction(){

        //다이얼로그를 정의하기위해 Dialog클래스를 생성
        final Dialog dlg = new Dialog(context);
        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.account_card_register_dialog);
        // 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText card_number1 = (EditText) dlg.findViewById(R.id.card_number1);
        final EditText card_number2 = (EditText) dlg.findViewById(R.id.card_number2);
        final EditText card_number3= (EditText) dlg.findViewById(R.id.card_number2);
        final EditText card_number4 = (EditText) dlg.findViewById(R.id.card_number3);

        final EditText validity_mm = (EditText) dlg.findViewById(R.id.validity_mm);
        final EditText validity_yy = (EditText) dlg.findViewById(R.id.validity_yy);

        final Button btn_car_register_cancel = (Button) dlg.findViewById(R.id.btn_card_register_cancel);
        final Button btn_car_register_ok = (Button) dlg.findViewById(R.id.btn_card_register_ok);

        btn_car_register_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 적힌 카드번호들을 리스트뷰에뿌려줘야함
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        btn_car_register_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
}
