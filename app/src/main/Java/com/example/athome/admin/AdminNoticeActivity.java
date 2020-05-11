package com.example.athome.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.athome.R;
import com.example.athome.notice.ItemNoticeData;
import com.example.athome.notice.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminNoticeActivity extends AppCompatActivity {
    private Button btn_back_notice;
    private Button btn_enroll;
    private ListView admin_notice_listView = null;
    private ArrayList<ItemNoticeData> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_notice);

        admin_notice_listView=(ListView)findViewById(R.id.admin_notice_listView);
        btn_back_notice = (Button)findViewById(R.id.admin_back_notice);
        btn_enroll = (Button)findViewById(R.id.notice_enrollBtn);
        btn_back_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        data=new ArrayList<>();

        //아이템들
        ItemNoticeData notice1=new ItemNoticeData("아차하면주차 아차파킹 버전1.0 출시","20.05.01",
                "안녕하세요? 아차파킹입니다." +
                        "" +
                        "2020년 5월 1일에 버전 1.0이 출시되었습니다. 저희 아차파킹은 공유주차서비스를 위한 어플리케이션으로 편한 주차를 소망하는 바람에서 제작되었습니다. 제작진에는 얼짱 정현정 , 따까리 김가정 외 3명이 있습니다." +
                        "" +
                        "이용해주셔서 감사합니다."
        );
        ItemNoticeData notice2=new ItemNoticeData("데이터 2","20.05.02","공지사항2");
        ItemNoticeData notice3=new ItemNoticeData("데이터 3","20.05.03","공지사항3");

        data.add(notice1);
        data.add(notice2);
        data.add(notice3);

        AdminListAdapter adapter=new AdminListAdapter(this,R.layout.admin_notice_listview_item,data);
        admin_notice_listView.setAdapter(adapter);

        admin_notice_listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?>parent,View view,int position, long id){
                    Intent intent = new Intent(getApplicationContext(),AdminNoticeClicked.class);

                    intent.putExtra("notieceTitle",data.get(position).getNoticeTitle());
                    intent.putExtra("noticeDate",data.get(position).getNoticeDate());
                    intent.putExtra("noticeContext",data.get(position).getNoticeContext());
                    startActivity(intent);

                }
        });
    }
}
