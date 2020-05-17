package com.example.athome.admin_notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.admin_enroll.AdminEnrollResult;
import com.example.athome.notice.ItemNoticeData;
import com.example.athome.notice.NoticeActivity;
import com.example.athome.retrofit.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AdminNoticeActivity extends AppCompatActivity {
    private Button btn_back_notice;
    private Button btn_enroll;
    private ListView admin_notice_listView = null;
    private List<ItemAdminNoticeData> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_notice);

        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");

        ApiService apiService = new RestRequestHelper().getApiService();
        final Call<AdminNoticeResult> res = apiService.allNotice(sharedToken,"good");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AdminNoticeResult adminNoticeResult = res.execute().body();
                    data = adminNoticeResult.getData();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ////

        AdminListAdapter adapter=new AdminListAdapter(this,R.layout.admin_notice_listview_item,data);
        admin_notice_listView.setAdapter(adapter);

        admin_notice_listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?>parent,View view,int position, long id){
                    Intent intent = new Intent(getApplicationContext(),AdminNoticeClicked.class);

                    intent.putExtra("noticeTitle",data.get(position).getNoticeTitle());
                    intent.putExtra("noticeDate",data.get(position).getNoticeDate());
                    intent.putExtra("noticeContext",data.get(position).getNoticeContext());
                    startActivity(intent);

                }
        });
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminNoticeNew.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
    }

    private void initialize() {
        admin_notice_listView=(ListView)findViewById(R.id.admin_notice_listView);
        btn_back_notice = (Button)findViewById(R.id.admin_back);
        btn_enroll = (Button)findViewById(R.id.notice_enrollBtn);
        btn_back_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
    }
}
