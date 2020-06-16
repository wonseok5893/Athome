package com.example.athome.admin_notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.retrofit.ApiService;

import java.io.IOException;

import retrofit2.Call;

public class AdminNoticeNew extends AppCompatActivity {
    private Button btn_back,btn_save;
    private EditText title,context;
    AddNoticeResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_notice_new);

        initalize();

    }

    private void initalize(){
        btn_save=(Button)findViewById(R.id.notice_new_saveBtn);
        btn_back = (Button)findViewById(R.id.btn_back_new);

        title = (EditText)findViewById(R.id.admin_notice_newTitle);
        context = (EditText)findViewById(R.id.admin_notice_newContext);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                String sharedToken = sf.getString("token", "");

                ApiService serviceApi = new RestRequestHelper().getApiService();
                final Call<AddNoticeResult> res = serviceApi.requestNoticeAdd(sharedToken, title.getText().toString(), context.getText().toString());

                Log.d("공지사항테스트", "지금 입력된값 : "+ title.getText().toString() + " " + context.getText().toString());

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
                    Log.d("NoticeNewError", "오류가 발생했어요.");
                }



            }
        });

    }
}
