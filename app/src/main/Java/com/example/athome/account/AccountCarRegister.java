package com.example.athome.account;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athome.R;

public class AccountCarRegister {
    private Context context;

    public AccountCarRegister(Context context){
        this.context=context;
    }

    //호출할 다이얼로그 함수를 정의
    public void callFunction(){

        //다이얼로그를 정의하기위해 Dialog클래스를 생성
        final Dialog dlg = new Dialog(context);
        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.account_car_register_dialog);
        // 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText car_number_edit = (EditText) dlg.findViewById(R.id.car_number_edit);
        final Button btn_car_register_cancel = (Button) dlg.findViewById(R.id.btn_car_register_cancel);
        final Button btn_car_register_ok = (Button) dlg.findViewById(R.id.btn_car_register_ok);

        btn_car_register_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 적힌 차량번호를 리스트뷰에뿌려줘야함
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
