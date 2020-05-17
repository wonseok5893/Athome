package com.example.athome.admin_enroll;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.admin.AdminResult;
import com.example.athome.retrofit.ApiService;

import org.w3c.dom.Text;

import java.net.IDN;

import retrofit2.Call;

public class AdminEnrollClicked extends AppCompatActivity {
    private Button btn_back;
    private Button btn_permit,btn_reject;
    AdminEnrollData data;
    AdminEnrollOwnerData userData;
    String sharedToken;
    String registerLocationRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_enroll_item_clicked);

        initialize();

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

                                ApiService serviceApi = new RestRequestHelper().getApiService();
                                final Call<AdminResult> registerRes = serviceApi.registerSharedLocation(sharedToken,userData.getUserId(),data.getId());
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            final AdminResult adminResult = registerRes.execute().body();
                                            registerLocationRes = adminResult.getRegisterLocationRes();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                try{Thread.sleep(200);}catch (Exception e){e.printStackTrace();}
                                if (registerLocationRes == null) {
                                    Log.d("TEST", "배정자 등록 오류");
                                } else if (registerLocationRes.equals("success")) {
                                    data.setState(1);
                                    Log.i("ADMIN",  "배정자 등록신청이 수락 되었습니다.");
                                    Toast.makeText(getApplicationContext(), "배정자 등록 완료 ", Toast.LENGTH_SHORT).show();

                                } else if (registerLocationRes.equals("fail")) {
                                    Log.d("TEST", "배정자 등록 실패.. ");
                                }
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }})
                        .show();

            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("취소 버튼");
                builder.setMessage("취소 하시겠습니까?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //수락시 처리로직
                        finish();
                    }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }})
                        .show();
            }
        });

    }
public void initialize() {
        Intent intent;
            btn_back=(Button)findViewById(R.id.admin_back_enroll_item);
            btn_permit = (Button)findViewById(R.id.admin_enroll_permit);
            btn_reject = (Button)findViewById(R.id.admin_enroll_reject);
            ImageView Img = (ImageView) findViewById(R.id.admin_enroll_img);
            TextView enroll_num = (TextView) findViewById(R.id.admin_enroll_num_value);
            TextView num = (TextView) findViewById(R.id.admin_enroll_num);
            TextView enroll_userId = (TextView) findViewById(R.id.admin_enroll_userId_value);
            TextView userId = (TextView) findViewById(R.id.admin_enroll_userId);
            TextView enroll_Phnum = (TextView) findViewById(R.id.admin_enroll_phnum_value);
            TextView phnum = (TextView) findViewById(R.id.admin_enroll_phnum);
            TextView enroll_birth = (TextView) findViewById(R.id.admin_enroll_birth_value);
            TextView birth = (TextView) findViewById(R.id.admin_enroll_birth);
            TextView enroll_loc = (TextView) findViewById(R.id.admin_enroll_loc_value);
            TextView loc = (TextView) findViewById(R.id.admin_enroll_loc);

            data =getIntent().getParcelableExtra("locationInfo");
            userData = getIntent().getParcelableExtra("userInfo");
            SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
            sharedToken = sf.getString("token", "");
            enroll_num.setText(data.getParkingInfo());
            enroll_loc.setText(data.getLocation() +"\n 위도:"+data.getLatitude()+"\n 경도:"+data.getLongitude());
            enroll_birth.setText(data.getUserBirth());
            enroll_userId.setText(userData.getUserId());
            enroll_Phnum.setText(userData.getUserPhone());
        }
}
