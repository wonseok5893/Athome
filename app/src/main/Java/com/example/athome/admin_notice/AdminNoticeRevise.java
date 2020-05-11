package com.example.athome.admin_notice;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.athome.R;

public class AdminNoticeRevise extends Activity{
    private Button btn_okay,btn_back;
    private EditText title,context,date;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_notice_revise);
        btn_back=(Button)findViewById(R.id.btn_back_revise);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_okay = (Button)findViewById(R.id.admin_notice_saveBtn);
        btn_okay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        title = (EditText)findViewById(R.id.admin_notice_reTitle);
        date = (EditText)findViewById(R.id.admin_notice_reDate);
        context=(EditText)findViewById(R.id.admin_notice_Recontext);

    }
}
