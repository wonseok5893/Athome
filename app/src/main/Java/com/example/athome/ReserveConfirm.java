package com.example.athome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.athome.main.MainActivity;

public class ReserveConfirm extends AppCompatActivity {
    private Button cancelBtn;
    private Button naviBtn;
    private ImageButton closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_confirm);
        naviBtn = (Button)findViewById(R.id.con_navi);
        closeBtn = (ImageButton)findViewById(R.id.close_btn);

        cancelBtn = (Button)findViewById(R.id.cancelBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //바로안내 버튼-> 경로 안내시작코드 수행해야함.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ReserveConfirm.this);
                dialog.setTitle("예약취소 확인")
                        .setMessage("예약을 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(ReserveConfirm.this,"예약이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), ReserveConfirm.class);
                                startActivity(intent);
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });
    }
}
