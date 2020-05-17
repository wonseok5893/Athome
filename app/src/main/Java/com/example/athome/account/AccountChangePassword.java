package com.example.athome.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athome.R;
import com.example.athome.User;
import com.example.athome.main.MainActivity;

public class AccountChangePassword extends Activity {
    private Button btn_back_change_password;
    private Button btn_change_pw;
    private EditText currentPassword;
    private EditText newPassword;
    private EditText newPasswordCheck;
    User user;
    String pw;
    String newPw;
    String newPwCheck;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_change_password);
        user = getIntent().getParcelableExtra("user");
        this.InitializeView();
        this.SetListener();

    }

    public void InitializeView() {
        btn_back_change_password = (Button) findViewById(R.id.btn_back_change_password);
        btn_change_pw = (Button) findViewById(R.id.btn_change_pw);

        currentPassword = findViewById(R.id.current_pw);
        newPassword = findViewById(R.id.new_pw);
        newPasswordCheck = findViewById(R.id.new_pw_check);

    }

    public void SetListener() {
        View.OnClickListener Listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_back_change_password: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
                        break;
                    case R.id.btn_change_pw: //확인 버튼
                        //비밀번화 변경 확인구현
                        newPw = newPassword.getText().toString();
                        newPwCheck = newPasswordCheck.getText().toString();
                        pw = currentPassword.getText().toString();
                        //검증
                        Log.d("비밀번호 변경 시도", pw + "->" + newPw);
                        if (newPw.isEmpty() || newPwCheck.isEmpty() || pw.isEmpty()) {
                            Toast.makeText(AccountChangePassword.this, "입력하지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!newPw.equals(newPwCheck)) {
                                Toast.makeText(AccountChangePassword.this, "새 비밀번호가 같지 않습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    user.editPassword(user.getToken(), pw, newPw);
                                    if (user.getEditPasswordRes().equals("success")) {
                                        Toast.makeText(AccountChangePassword.this, "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(AccountChangePassword.this, user.getEditPasswordMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(AccountChangePassword.this, "비밀번호를 수정하는 과정에서 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        break;
                }
            }
        };
        btn_back_change_password.setOnClickListener(Listener);
        btn_change_pw.setOnClickListener(Listener);
    }

}
