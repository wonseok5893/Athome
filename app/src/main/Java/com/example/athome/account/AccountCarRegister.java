package com.example.athome.account;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.main.MainActivity;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.EditCarNumberResult;

import java.io.IOException;

import retrofit2.Call;

import static android.content.Context.MODE_PRIVATE;

public class AccountCarRegister {
    private Context context;
    private EditCarNumberResult editRes;
    OnMyDialogResult mDialogResult;

    public AccountCarRegister(Context context) {
        this.context = context;
    }

    //호출할 다이얼로그 함수를 정의
    public void callFunction() {

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

                SharedPreferences sf = context.getSharedPreferences("token", MODE_PRIVATE);
                String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기
                ApiService serviceApi = new RestRequestHelper().getApiService();
                final Call<EditCarNumberResult> res = serviceApi.editCarNumber(sharedToken, car_number_edit.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            editRes = res.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (editRes != null) {
                    if (editRes.getResult().equals("success")) {
                        Toast.makeText(context, editRes.getMessage(), Toast.LENGTH_SHORT).show();
                        if( mDialogResult != null ){
                            mDialogResult.finish(car_number_edit.getText().toString());
                            dlg.dismiss();
                        }else
                            Toast.makeText(context, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, editRes.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                }

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

    public void setDialogResult(OnMyDialogResult dialogResult){

        mDialogResult = dialogResult;

    }



    public interface OnMyDialogResult{

        void finish(String result);

    }





}
