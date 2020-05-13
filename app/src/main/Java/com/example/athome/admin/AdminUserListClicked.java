package com.example.athome.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.athome.R;

public class AdminUserListClicked extends AppCompatActivity {
    private Button btn_back,btn_share,btn_share_past,btn_resv,btn_resv_past;
    private TextView id,pw,phnum,point,permit;
    private EditText new_pw=null,new_phnum=null,new_point=null,new_permit=null;
    private Button btn_save_pw,btn_save_point,btn_save_phnum,btn_save_permit;
    private ListView carlistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_detail_clicked);

        btn_back=(Button)findViewById(R.id.admin_detail_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_resv=(Button)findViewById(R.id.btn_clicked_reserv);
        btn_resv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminUserClickedReservActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_resv_past=(Button)findViewById(R.id.btn_clicked_past_reserv);
        btn_resv_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminUserClickedReservPastActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
        btn_share=(Button)findViewById(R.id.btn_clicked_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminUserClickedShareActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        btn_share_past=(Button)findViewById(R.id.btn_clicked_share_past);
        btn_share_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminUserClickedSharePastActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
        btn_save_pw=(Button)findViewById(R.id.user_pw_save_btn);
        btn_save_point=(Button)findViewById(R.id.user_point_save_btn);
        btn_save_phnum=(Button)findViewById(R.id.user_ph_save_btn);
        btn_save_permit=(Button)findViewById(R.id.user_permit_save_btn);
        carlistView=(ListView)findViewById(R.id.user_clicked_carlist);

    }
    public void initialize(){
        id = findViewById(R.id.user_clicked_id);
        pw = findViewById(R.id.user_clicked_pw);
        phnum = findViewById(R.id.user_clicked_phnum);
        point = findViewById(R.id.user_clicked_point);
        permit = findViewById(R.id.user_clicked_permit);
    }

}
