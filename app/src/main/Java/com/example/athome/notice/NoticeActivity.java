package com.example.athome.notice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {
    private Button btn_back_notice;
    private ListView m_oListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        btn_back_notice = (Button) findViewById(R.id.btn_back_notice);
        btn_back_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        String[] noticeDate = {"2017-01-03", "1965-02-23", "2016-04-13", "2010-01-01", "2017-06-20",
                "2012-07-08", "1980-04-14", "2016-09-26", "2014-10-11", "2010-12-24"};
        int nDatCnt = 0;
        ArrayList<ItemData> oData = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            ItemData oItem = new ItemData();
            oItem.noticeTitle = "데이터 " + (i + 1);
            oItem.noticeDate = noticeDate[nDatCnt++];
            oData.add(oItem);
            if (nDatCnt >= noticeDate.length) nDatCnt = 0;
        }

// ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView) findViewById(R.id.listView);
        ListAdapter oAdapter = new ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);
    }


}
