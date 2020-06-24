package com.example.athome.admin_enroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.athome.R;
import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.retrofit.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AdminEnrollActivity extends AppCompatActivity {
    private Button btn_back;
    private ListView admin_enroll_listView = null;
    private List<AdminEnrollData> data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_enroll);

        //모든 정보 다받아옴
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");

        ApiService apiService = new RestRequestHelper().getApiService();
        final Call<AdminEnrollResult> res = apiService.getUncheckedSharedLocation(sharedToken,"good");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AdminEnrollResult adminEnrollResult = res.execute().body();
                    data = adminEnrollResult.getData();
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

        btn_back = (Button)findViewById(R.id.admin_back_enroll);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        admin_enroll_listView = findViewById(R.id.admin_enroll_listView);
        AdminEnrollAdapter adminEnrollAdapter = new AdminEnrollAdapter(this,R.layout.admin_enroll_listview_item, (ArrayList<AdminEnrollData>) data);

        admin_enroll_listView.setAdapter(adminEnrollAdapter);


        //아이템 클릭시 작동
        admin_enroll_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(),AdminEnrollClicked.class);
                        intent.putExtra("locationInfo",  data.get(position));
                        intent.putExtra("userInfo",data.get(position).getOwner());
                        startActivity(intent);
            }
        });

    }
}
