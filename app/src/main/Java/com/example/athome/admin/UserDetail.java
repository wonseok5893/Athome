package com.example.athome.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.athome.R;

public class UserDetail extends Activity {
    private Button backButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.notice_item_clicked);
        backButton=(Button)findViewById(R.id.admin_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });


//
//
//        TextView noticeTitle=(TextView)findViewById(R.id.notice_item_title);
//        TextView noticeDate=(TextView)findViewById(R.id.notice_item_date);
//        TextView noticeContext=(TextView)findViewById(R.id.notice_item_context);
//
//        noticeTitle.setText(intent.getStringExtra("noticeTitle"));
//        noticeDate.setText(intent.getStringExtra("noticeDate"));
//        noticeContext.setText(intent.getStringExtra("noticeContext"));



    }
}

