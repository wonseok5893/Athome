package com.example.athome.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.athome.R;

import java.util.List;

public class AdminUserClickedShareActivity extends AppCompatActivity {
    private Button btn_back,btn_cancel,btn_delete;
    private List<AdminUserClickedShareData> data = null;
    private ListView shareListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_clicked_share);

        shareListView=(ListView)findViewById(R.id.share_listview);

        btn_back=(Button) findViewById(R.id.btn_back_new);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_cancel=(Button)findViewById(R.id.clicked_share_state_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //예약상태 변경해야함 !!!!!!!
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
        btn_delete= (Button)findViewById(R.id.clicked_share_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 삭제  !!!!!!!
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
    }
}
