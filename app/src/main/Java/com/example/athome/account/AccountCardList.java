package com.example.athome.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.athome.R;

public class AccountCardList extends Activity {

    private Button btn_back_card_list;
    private Button btn_card_register;
    private Intent intent;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_card_list);

        this.InitializeView();
        this.SetListner();

    }

    public void InitializeView(){
        btn_back_card_list=(Button)findViewById(R.id.btn_back_card_list);
        btn_card_register=(Button)findViewById(R.id.btn_card_register);

    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_card_list: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.btn_card_register: //등록하기 버튼
                        Intent intent = new Intent(getApplicationContext(), AccountCardRegister.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        btn_card_register.setOnClickListener(Listener);
        btn_back_card_list.setOnClickListener(Listener);
    }
}
