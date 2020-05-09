package com.example.athome.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.athome.R;

public class AccountChangePassword extends Activity {
    private Button btn_back_change_password;
    private Button btn_change_pw_cancel;
    private Button btn_change_pw;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_change_password);

        this.InitializeView();
        this.SetListner();

    }

    public void InitializeView(){
        btn_back_change_password=(Button)findViewById(R.id.btn_back_change_password);
        btn_change_pw_cancel=(Button)findViewById(R.id.btn_change_pw_cancel);
        btn_change_pw=(Button)findViewById(R.id.btn_change_pw);

    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_change_password: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.btn_change_pw_cancel: //취소 버튼
                        finish();
                        break;
                    case R.id.btn_change_pw: //확인 버튼
                        //비밀번화 변경 확인구현
                        break;
                }
            }
        };
        btn_back_change_password.setOnClickListener(Listener);
        btn_change_pw_cancel.setOnClickListener(Listener);
        btn_change_pw.setOnClickListener(Listener);
    }

}
