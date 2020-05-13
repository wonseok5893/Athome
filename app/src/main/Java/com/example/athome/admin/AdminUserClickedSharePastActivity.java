package com.example.athome.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.athome.R;

import java.util.List;

public class AdminUserClickedSharePastActivity extends AppCompatActivity {
    private Button btn_back,btn_delete;
    private List<AdminUserClickedShareData> data = null;
    private  ListView sharePastListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_clicked_share_past);

        sharePastListView=(ListView)findViewById(R.id.share_past_listview);
        btn_back=(Button) findViewById(R.id.clicked_reserv_past_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });


        btn_delete= (Button)findViewById(R.id.clicked_share_past_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 삭제  !!!!!!!
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
    }
}
