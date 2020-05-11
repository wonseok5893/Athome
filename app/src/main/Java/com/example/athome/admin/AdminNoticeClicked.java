package com.example.athome.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.athome.R;

public class AdminNoticeClicked extends Activity {
    private Button btn_back_notice_item=null;
    private Button btn_delete,btn_revise;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_notice_item_clicked);
        btn_back_notice_item=(Button)findViewById(R.id.admin_back_notice_item);
        btn_back_notice_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminNoticeRevise.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        Intent intent=getIntent();

        TextView noticeTitle=(TextView)findViewById(R.id.admin_notice_title);
        TextView noticeDate=(TextView)findViewById(R.id.admin_notice_date);
        TextView noticeContext=(TextView)findViewById(R.id.admin_notice_context);

        noticeTitle.setText(intent.getStringExtra("noticeTitle"));
        noticeDate.setText(intent.getStringExtra("noticeDate"));
        noticeContext.setText(intent.getStringExtra("noticeContext"));
    }
}
