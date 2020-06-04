package com.example.athome.notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.athome.R;

public class NoticeClicked extends Activity {
    private Button btn_back_notice_item=null;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.notice_item_clicked);
        btn_back_notice_item=(Button)findViewById(R.id.btn_back_notice_item);
        btn_back_notice_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });


        Intent intent=getIntent();

        TextView noticeTitle=(TextView)findViewById(R.id.notice_item_title);
        TextView noticeDate=(TextView)findViewById(R.id.notice_item_date);
        TextView noticeContext=(TextView)findViewById(R.id.notice_item_context);

        noticeTitle.setText(intent.getStringExtra("noticeTitle"));
        noticeDate.setText(intent.getStringExtra("noticeDate"));
        noticeContext.setText(intent.getStringExtra("noticeDescription"));



    }
}

