package com.example.athome.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athome.R;
import com.example.athome.User;
import com.example.athome.main.MainActivity;

public class AccountChangeEmail extends Activity {
    private Button btn_back_change_email;
    private Button btn_email_change;
    private EditText new_email;



    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_change_email);

        this.InitializeView();
        this.SetListener();
    }
    public void InitializeView() {
        btn_back_change_email = (Button) findViewById(R.id.btn_back_change_email);
        btn_email_change = (Button)findViewById(R.id.btn_email_change);
        new_email=(EditText)findViewById(R.id.new_email);
    }

    public void SetListener() {
        View.OnClickListener Listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_back_change_email: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
                        break;
                    case R.id.btn_email_change: //확인 버튼


                        break;
                }
            }
        };
        btn_back_change_email.setOnClickListener(Listener);
        btn_email_change.setOnClickListener(Listener);
    }
}
