package com.example.athome.notification;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;

import java.util.ArrayList;


public class NotificationActivity extends AppCompatActivity {
    private Button btn_back_notification;
    private ListView notification_listView = null;
    private ArrayList<ItemNotificationData> data=null;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_box);

        this.InitializeView();
        this.SetListner();

        ItemNotificationData data1=new ItemNotificationData("입차 10분전 입니다","서진주택 공유주차장 입차 10분전 입니다!","05/17","15:50");
        ItemNotificationData data2=new ItemNotificationData("출차 10분전 입니다","서진주택 공유주차장 출차 10분전 입니다!","05/17","16:50");
        data.add(data1);
        data.add(data2);

        adapter=new NotificationAdapter(this,R.layout.notice_listview_item,data);
        notification_listView.setAdapter(adapter);
    }

    public void InitializeView(){
        btn_back_notification=(Button)findViewById(R.id.btn_back_notification);
        notification_listView=(ListView)findViewById(R.id.notification_listView);
        data = new ArrayList<>();
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_notification: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                }
            }
        };
        btn_back_notification.setOnClickListener(Listener);
    }

}
