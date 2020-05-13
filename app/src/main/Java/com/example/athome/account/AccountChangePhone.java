package com.example.athome.account;

import android.app.Activity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.athome.R;


public class AccountChangePhone  extends Activity {
    private Button btn_back_phone_change;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_change_phone);

        this.InitializeView();
        this.SetListener();

    }

    public void InitializeView() {
        btn_back_phone_change = (Button) findViewById(R.id.btn_back_phone_change);


    }

    public void SetListener() {
        View.OnClickListener Listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_back_phone_change: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
                        break;

                }
            }
        };
        btn_back_phone_change.setOnClickListener(Listener);
    }
}
