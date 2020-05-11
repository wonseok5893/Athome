package com.example.athome.admin_enroll;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.athome.R;
import com.example.athome.admin_enroll.AdminEnrollData;
import com.example.athome.notice.ItemNoticeData;

import java.util.ArrayList;
public class AdminEnrollActivity extends AppCompatActivity {
    private Button btn_back;
    private ListView admin_notice_listView = null;
    private ArrayList<AdminEnrollData> data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_enroll);

        btn_back = (Button)findViewById(R.id.admin_back_enroll);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        data=new ArrayList<>();

        //아이템들
        AdminEnrollData notice1=new AdminEnrollData("hello","010122222333","수원시 영통구");
        AdminEnrollData notice2=new AdminEnrollData("데이터 2","20.05.02","공지사항2");
        AdminEnrollData notice3=new AdminEnrollData("데이터 3","20.05.03","공지사항3");

        data.add(notice1);
        data.add(notice2);
        data.add(notice3);


    }
}
