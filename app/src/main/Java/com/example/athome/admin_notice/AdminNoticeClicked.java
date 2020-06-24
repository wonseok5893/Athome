package com.example.athome.admin_notice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.athome.R;
import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.retrofit.ApiService;

import java.io.IOException;

import retrofit2.Call;

public class AdminNoticeClicked extends Activity {
    private Button btn_back_notice_item=null;
    private Button btn_delete,btn_revise;
    private AdminDeleteNoticeResult result;
    private String id, title, context, date;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_notice_item_clicked);

        initialize();
        setListener();

    }

    void initialize() {
        Intent intent=getIntent();

        TextView noticeTitle=(TextView)findViewById(R.id.admin_notice_title);
        TextView noticeDate=(TextView)findViewById(R.id.admin_notice_date);
        TextView noticeContext=(TextView)findViewById(R.id.admin_notice_context);

        noticeTitle.setText(intent.getStringExtra("noticeTitle"));
        noticeDate.setText(intent.getStringExtra("noticeDate"));
        noticeContext.setText(intent.getStringExtra("noticeContext"));

        btn_back_notice_item=(Button)findViewById(R.id.admin_back_notice_item);
        btn_revise = findViewById(R.id.admin_reviseBtn);
        btn_delete = findViewById(R.id.admin_deleteBtn);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("noticeTitle");
        date = getIntent().getStringExtra("noticeDate");
        context = getIntent().getStringExtra("noticeContext");

    }

    void setListener() {
        btn_back_notice_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
        btn_revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminNoticeRevise.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("date", date);
                intent.putExtra("context", context);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                String sharedToken = sf.getString("token", "");

                ApiService serviceApi = new RestRequestHelper().getApiService();
                final Call<AdminDeleteNoticeResult> res = serviceApi.requestNoticeDelete(sharedToken, id);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = res.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {

                    if(result.getResult().equals("success")) {
                        Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), com.example.athome.main.MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("NoticeDeleteError", "오류가 발생했어요.");
                }
            }

        });

    }
}
