package com.example.athome.admin_enroll;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.athome.R;

import java.util.ArrayList;

public class AdminCarlistActivity extends AppCompatActivity {
    private Button btn_back;
    private Button btn_ok;
    private Button btn_reject;
    ArrayList<AdminCarlistData>data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_carlist);

        btn_back = (Button) findViewById(R.id.admin_back_enroll);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });

        btn_ok = (Button) findViewById(R.id.carlist_okay);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 서버에 데이터 넣어야함!!!
                show_okay();
                finish();
            }
        });

        btn_reject = (Button) findViewById(R.id.carlist_reject);
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_reject();
                finish();
            }
        });

        data = new ArrayList<>();

        AdminCarlistData data1 = new AdminCarlistData("hello", "gajeong", "yeosin");

        data.add(data1);


    }
    void show_reject () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("보류창");
        builder.setMessage("등록을 거절하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "보류", Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "아니오", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    void show_okay () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("확인창");
        builder.setMessage("등록을 수락하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "수락완료", Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "아니오", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
}
