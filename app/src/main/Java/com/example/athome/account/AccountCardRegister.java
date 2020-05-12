package com.example.athome.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.athome.R;

public class AccountCardRegister extends Activity {
    private Button btn_card_register_cancel;
    private Button btn_card_register_ok;
    EditText card_number1,card_number2,card_number3,card_number4;
    EditText validity_mm,validity_yy;
    ItemAccountCardData data;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_card_register);

        this.InitializeView();
        this.SetListner();

    }

    public void InitializeView(){
        btn_card_register_cancel=(Button)findViewById(R.id.btn_card_register_cancel);
        btn_card_register_ok=(Button)findViewById(R.id.btn_card_register_ok);
        card_number1=(EditText)findViewById(R.id.card_number1);
        card_number2=(EditText) findViewById(R.id.card_number2);
        card_number3=(EditText) findViewById(R.id.card_number3);
        card_number4=(EditText) findViewById(R.id.card_number4);
        validity_mm=(EditText) findViewById(R.id.validity_mm);
        validity_yy=(EditText) findViewById(R.id.validity_yy);

    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_card_register_cancel: //취소 버튼
                        finish();
                        break;
                    case R.id.btn_card_register_ok: //등록하기 버튼

                        break;
                }
            }
        };
        btn_card_register_cancel.setOnClickListener(Listener);
        btn_card_register_ok.setOnClickListener(Listener);
    }
}
