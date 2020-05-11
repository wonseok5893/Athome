package com.example.athome.admin_enroll;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athome.R;

import org.w3c.dom.Text;

import java.net.IDN;

public class AdminEnrollClicked extends AppCompatActivity {
    private Button btn_back;
    private Button btn_permit,btn_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_enroll_item_clicked);

        btn_back=(Button)findViewById(R.id.admin_back_enroll_item);
        btn_permit = (Button)findViewById(R.id.admin_enroll_permit);
        btn_reject = (Button)findViewById(R.id.admin_enroll_reject);
        final AlertDialog.Builder builder=new AlertDialog.Builder(AdminEnrollClicked.this);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });
        btn_permit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("수락버튼");
                        builder.setMessage("수락하시겠습니까?");
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //확인시 처리 로직
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }})
                        .show();

            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("수락버튼");
                builder.setMessage("수락하시겠습니까?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //수락시 처리로직
                        finish();
                    }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }})
                        .show();
            }
        });

        Intent intent=getIntent();

        ImageView Img = (ImageView) findViewById(R.id.admin_enroll_img);
        TextView enroll_num = (TextView)findViewById(R.id.admin_enroll_num_value);
        TextView num = (TextView)findViewById(R.id.admin_enroll_num);
        TextView enroll_userId = (TextView)findViewById(R.id.admin_enroll_userId_value);
        TextView userId = (TextView)findViewById(R.id.admin_enroll_userId);
        TextView enroll_Phnum = (TextView)findViewById(R.id.admin_enroll_phnum_value);
        TextView phnum = (TextView)findViewById(R.id.admin_enroll_phnum);
        TextView enroll_birth = (TextView)findViewById(R.id.admin_enroll_birth_value);
        TextView birth = (TextView)findViewById(R.id.admin_enroll_birth);
        TextView enroll_loc = (TextView)findViewById(R.id.admin_enroll_loc_value);
        TextView loc = (TextView)findViewById(R.id.admin_enroll_loc);

        enroll_num.setText(intent.getStringExtra("enroll_num"));
        enroll_loc.setText(intent.getStringExtra("enroll_loc"));
        enroll_birth.setText(intent.getStringExtra("enroll_birth"));
        enroll_userId.setText(intent.getStringExtra("enroll_userId"));
        enroll_Phnum.setText(intent.getStringExtra("enroll_Phnum"));

    }
}
