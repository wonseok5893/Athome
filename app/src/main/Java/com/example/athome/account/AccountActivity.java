package com.example.athome.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;

public class AccountActivity extends AppCompatActivity {
    //필독바람!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //더 추가해야할 부분
    //각 linearlayout에 해당하는 텍스트뷰 아이디들
    //userid_value
    //userphone_value
    //usercar_value
    //usercard_value
    //useremail_vale
    //에 데이터 값을 받아와야함
    //그리고 데이터 값이 존재한다면 기존에 있는 텍스트는 받아온 데이터로 변경된 후 데이터의 색은 파란색으로 지정
    //데이터 값이 존재하지 않는다면 기존 텍스트는 "없음"으로 변경된 후 색은 빨간색으로 지정

    private LinearLayout userIdLinear;
    private LinearLayout userPasswordLinear;
    private LinearLayout userPhoneLinear;
    private LinearLayout userCarLinear;
    private LinearLayout userCardLinear;
    private LinearLayout userEmailLinear;
    private Button btn_back_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.InitializeView();
        this.SetListner();
    }

    public void InitializeView(){
        userIdLinear =(LinearLayout)findViewById(R.id.userid_linear);
        userPasswordLinear=(LinearLayout)findViewById(R.id.userpw_linear);
        userPhoneLinear=(LinearLayout)findViewById(R.id.userphone_linear);
        userCarLinear=(LinearLayout)findViewById(R.id.usercar_linear);
        userCardLinear=(LinearLayout)findViewById(R.id.usercard_linear);
        userEmailLinear=(LinearLayout)findViewById(R.id.useremail_linear);
        btn_back_account=(Button)findViewById(R.id.btn_back_account);
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_account:
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.userid_linear: //클릭시 로그아웃
                        Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.userpw_linear://클릭시 비밀번호 변경하는 레이아웃으로 이동
                        Toast.makeText(getApplicationContext(), "비밀번호 변경", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.userphone_linear://클릭시 핸드폰번호 변경하는 레이아웃으로 이동
                        Toast.makeText(getApplicationContext(), "핸드폰번호", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.usercar_linear://클릭시 차량번호 등록,삭제하는 레이아웃으로 이동
                        Toast.makeText(getApplicationContext(), "차량번호", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.usercard_linear://클릭시 카드 추가, 삭제하는 레이아웃으로 이동
                        Toast.makeText(getApplicationContext(), "카드", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.useremail_linear://클릭시 이메일 변경하는 레이아웃으로 이동
                        Toast.makeText(getApplicationContext(), "이메일", Toast.LENGTH_LONG).show();
                        break;


                }
            }
        };
        btn_back_account.setOnClickListener(Listener);
        userIdLinear.setOnClickListener(Listener);
        userPasswordLinear.setOnClickListener(Listener);
        userPhoneLinear.setOnClickListener(Listener);
        userCarLinear.setOnClickListener(Listener);
        userCardLinear.setOnClickListener(Listener);
        userEmailLinear.setOnClickListener(Listener);
    }
}
