package com.example.athome.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;
import com.example.athome.retrofit.RestRequestHelper;

import com.example.athome.retrofit.ApiService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class NoticeActivity extends AppCompatActivity {
    private Button btn_back_notice;
    private ListView notice_listView = null;
    private List<ItemNoticeData> data=null;
    private ItemNoticeResult itemNoticeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ApiService apiService = new RestRequestHelper().getApiService();
            final Call<ItemNoticeResult> res = apiService.allNotice("1234");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        itemNoticeResult = res.execute().body();
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
            if(itemNoticeResult != null){
                data = itemNoticeResult.getData();
            }


        notice_listView=(ListView)findViewById(R.id.notice_listView);
        btn_back_notice=(Button)findViewById(R.id.btn_back_notice);
        btn_back_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });



        //리스트 속의 아이템 연결
        ItemNoticeListAdaptor adapter=new ItemNoticeListAdaptor(this,R.layout.notice_listview_item,data);
        notice_listView.setAdapter(adapter);

        //아이템 클릭시 작동
        notice_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),NoticeClicked.class);

                //putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값
                intent.putExtra("noticeTitle",data.get(position).getTitle());
                intent.putExtra("noticeDate",data.get(position).getEnrollTime());
                intent.putExtra("noticeDescription",data.get(position).getDescription());
                intent.putExtra("id",data.get(position).getId());
                startActivity(intent);
            }
        });
    }


}

