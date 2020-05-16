package com.example.athome.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.EditResult;

import retrofit2.Call;

public class AdminUserListClicked extends AppCompatActivity {
    private Button btn_back, btn_share, btn_share_past, btn_resv, btn_resv_past;
    private TextView id, pw, phnum, point, permit;
    private EditText editPassword, editPhone, editPermit, editPoint;
    private Button btn_save_pw, btn_save_point, btn_save_phnum, btn_save_permit;
    private ListView carlistView;
    AllUserData user;
    String adminEditPasswordResult;
    String sharedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_detail_clicked);

        //user 데이터 가져오기
        user = getIntent().getParcelableExtra("userInfo");
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        sharedToken = sf.getString("token", "");
        // find view by Id
        initialize();
        // 데이터 가
        id.setText(user.getUserId());
        pw.setText(user.getUserPassword());
        phnum.setText(user.getUserPhone());
        if (user.getState() != 0)
            permit.setText("관리자");
        else
            permit.setText("사용자");
        point.setText(user.getPoint().toString());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });


        btn_resv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminUserClickedReservActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });


        btn_resv_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminUserClickedReservPastActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminUserClickedShareActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });


        btn_share_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminUserClickedSharePastActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });

        //비밀번호 수정 버튼
        btn_save_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService serviceApi = new RestRequestHelper().getApiService();
                final Call<AdminResult> editRes = serviceApi.adminEditPassword(sharedToken, editPassword.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final AdminResult adminResult = editRes.execute().body();
                            adminEditPasswordResult = adminResult.getEditPasswordRes();
                           } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                if (adminEditPasswordResult == null) {
                    Log.d("TEST", "비밀번호 변경 오류");
                } else if (adminEditPasswordResult.equals("success")) {
                    user.setUserPassword(editPassword.getText().toString());
                    pw.setText(user.getUserPassword());
                    Log.i("ADMIN",  user.getUserId()+ " 비밀번호가 " + editPassword.getText().toString() + "로 바뀌었습니다");
                    Toast.makeText(getApplicationContext(), user.getUserId() + " 비밀번호가 " + editPassword.getText().toString() + "으로 바뀌었습니다", Toast.LENGTH_SHORT).show();

                } else if (adminEditPasswordResult.equals("fail")) {
                    Log.d("TEST", "비밀번호 변경 실패.. ");
                }
            }
        });

    }

    public void initialize() {
        id = findViewById(R.id.user_clicked_id);
        pw = findViewById(R.id.user_clicked_pw);
        phnum = findViewById(R.id.user_clicked_phnum);
        point = findViewById(R.id.user_clicked_point);
        permit = findViewById(R.id.user_clicked_permit);
        btn_resv = (Button) findViewById(R.id.btn_clicked_reserv);
        btn_back = (Button) findViewById(R.id.admin_detail_back);
        btn_save_pw = (Button) findViewById(R.id.user_pw_save_btn);
        btn_save_point = (Button) findViewById(R.id.user_point_save_btn);
        btn_save_phnum = (Button) findViewById(R.id.user_ph_save_btn);
        btn_save_permit = (Button) findViewById(R.id.user_permit_save_btn);
        carlistView = (ListView) findViewById(R.id.user_clicked_carlist);
        btn_share_past = (Button) findViewById(R.id.btn_clicked_share_past);
        btn_share = (Button) findViewById(R.id.btn_clicked_share);
        btn_resv_past = (Button) findViewById(R.id.btn_clicked_past_reserv);
        editPassword = findViewById(R.id.user_clicked_pw_new);
        editPhone = findViewById(R.id.user_clicked_ph_new);
        editPoint = findViewById(R.id.user_clicked_point_new);
        editPermit = findViewById(R.id.user_clicked_permit_new);

    }

}
