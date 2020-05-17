package com.example.athome.admin_notice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.athome.R;

public class AdminNoticeNew extends AppCompatActivity {
    private Button btn_back,btn_save;
    private EditText title=null,context=null,date=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_notice_new);
    }

    private void Initalize(){
        btn_save=(Button)findViewById(R.id.notice_new_saveBtn);
        btn_back = (Button)findViewById(R.id.btn_back_new);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
        title = (EditText)findViewById(R.id.admin_notice_newTitle);
        context = (EditText)findViewById(R.id.admin_notice_newContext);
        date = (EditText)findViewById(R.id.admin_notice_newDate);

    }
}
