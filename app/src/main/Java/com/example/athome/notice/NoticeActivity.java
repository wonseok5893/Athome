package com.example.athome.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;
import com.example.athome.admin_enroll.AdminCarlistAdapter;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {
    private Button btn_back_notice;
    private ListView notice_listView = null;
    private ArrayList<ItemNoticeData> data=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        notice_listView=(ListView)findViewById(R.id.notice_listView);
        btn_back_notice=(Button)findViewById(R.id.btn_back_notice);
        btn_back_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        //서버랑 연동해서 값을 띄어야함
        //일단 하드코딩으로 값 집어넣음
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
        ItemNoticeData notice4=new ItemNoticeData("데이터 4","20.05.04","공지사항4");
        ItemNoticeData notice5=new ItemNoticeData("데이터 5","20.05.05","공지사항5");
        ItemNoticeData notice6=new ItemNoticeData("데이터 6","20.05.06","공지사항6");
        ItemNoticeData notice7=new ItemNoticeData("데이터 7","20.05.07","공지사항7");
        ItemNoticeData notice8=new ItemNoticeData("데이터 8","20.05.08","공지사항8");
        ItemNoticeData notice9=new ItemNoticeData("데이터 9","20.05.09","공지사항9");
        ItemNoticeData notice10=new ItemNoticeData("데이터 10","20.05.10","공지사항10");

        //리스트에 추가
        data.add(notice1);
        data.add(notice2);
        data.add(notice3);
        data.add(notice4);
        data.add(notice5);
        data.add(notice6);
        data.add(notice7);
        data.add(notice8);
        data.add(notice9);
        data.add(notice10);

        //리스트 속의 아이템 연결
        AdminCarlistAdapter adapter=new AdminCarlistAdapter(this,R.layout.notice_listview_item,data);
        notice_listView.setAdapter(adapter);

        //아이템 클릭시 작동
        notice_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),NoticeClicked.class);

                //putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값
                intent.putExtra("noticeTitle",data.get(position).getNoticeTitle());
                intent.putExtra("noticeDate",data.get(position).getNoticeDate());
                intent.putExtra("noticeContext",data.get(position).getNoticeContext());
                startActivity(intent);
            }
        });
    }


}

