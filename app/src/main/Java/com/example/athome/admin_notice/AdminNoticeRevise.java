package com.example.athome.admin_notice;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athome.R;
import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.retrofit.ApiService;

import java.io.IOException;

import retrofit2.Call;

public class AdminNoticeRevise extends Activity{
    private Button btn_okay,btn_back;
    private String id;
    private EditText title,context;
    private TextView date;
    private ReviseNoticeResult rns;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.admin_notice_revise);

        initialize();
        setListener();
    }

    void initialize() {

        btn_back=(Button)findViewById(R.id.btn_back_revise);

        btn_okay = (Button)findViewById(R.id.admin_notice_saveBtn);

        title = (EditText)findViewById(R.id.admin_notice_reTitle);
        date = (TextView)findViewById(R.id.admin_notice_reDate);
        context=(EditText)findViewById(R.id.admin_notice_Recontext);

        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        title.setText(intent.getStringExtra("title"));
        date.setText(intent.getStringExtra("date"));
        context.setText(intent.getStringExtra("context"));
    }

    void setListener() {
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_okay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                String sharedToken = sf.getString("token", "");

                ApiService serviceApi = new RestRequestHelper().getApiService();
                final Call<ReviseNoticeResult> res = serviceApi.requestNoticeRevise(sharedToken, id, title.getText().toString(), context.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            rns = res.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                // Log.d("공지사항", "바뀔 값들 : " + id+ title.getText().toString()+ context.getText().toString());

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    if(rns.getResult().equals("success")) {
                        Toast.makeText(getApplicationContext(), rns.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), com.example.athome.main.MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), rns.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch(Exception e) {
                    Log.d("NoticeNewReviseError", "오류가 발생했어요.");
                }


            }
        });
    }
}
