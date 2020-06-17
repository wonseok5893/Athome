package com.example.athome.admin_notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class AdminNoticeActivity extends AppCompatActivity {
    private Button btn_back_notice;
    private FloatingActionButton btn_enroll;
    private ListView admin_notice_listView = null;
    private List<ItemAdminNoticeData> data = null;
    private AdminNoticeResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_notice);

        initialize();

        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");

        ApiService apiService = new RestRequestHelper().getApiService();
        final Call<AdminNoticeResult> res = apiService.getAdminNotice(sharedToken, "");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result = res.execute().body();
                    data = result.getData();
                } catch (IOException ie) {
                    ie.printStackTrace();
                } catch (NullPointerException ne) {
                    Log.d("junggyu", "값 없음");
                    ne.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AdminListAdapter adapter=new AdminListAdapter(this,R.layout.admin_notice_listview_item,data);
        admin_notice_listView.setAdapter(adapter);

        admin_notice_listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?>parent,View view,int position, long id){
                Intent intent = new Intent(getApplicationContext(),AdminNoticeClicked.class);
                intent.putExtra("id",data.get(position).getId());
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
        btn_enroll = (FloatingActionButton)findViewById(R.id.notice_enrollBtn);
        btn_back_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
    }
}
