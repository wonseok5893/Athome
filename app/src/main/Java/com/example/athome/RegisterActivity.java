package com.example.athome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.main.User;

import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private EditText sign_id, sign_pw, sign_check_pw, sign_name, sign_email, sign_phone, sign_car_number;
    private Button signUpButton, signUpCancelButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sign_id = findViewById(R.id.sign_id);
        sign_pw = findViewById(R.id.sign_pw);
        sign_check_pw = findViewById(R.id.sign_check_pw);
        sign_name = findViewById(R.id.sign_name);
        sign_email = findViewById(R.id.sign_email);
        sign_phone = findViewById(R.id.sign_phone);

        signUpButton = findViewById(R.id.space_resv);
        signUpCancelButton = findViewById(R.id.space_navi);

        signUpCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUpButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = sign_id.getText().toString();
                String userPassword = sign_pw.getText().toString();
                String userCheckPassword = sign_check_pw.getText().toString();
                String userName = sign_name.getText().toString();
                String userEmail = sign_email.getText().toString();
                String userPhone = sign_phone.getText().toString();

                //회원가입 Client 검증
                if (userId.isEmpty() || userPassword.isEmpty() || userCheckPassword.isEmpty() || userName.isEmpty() || userEmail.isEmpty() ||
                        userPhone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 항목을 기입하십시오.", Toast.LENGTH_SHORT).show();
                }else if(!Pattern.matches("^[a-zA-Z0-9]+$",userId)){
                    Toast.makeText(getApplicationContext(), "아이디는 영문자와 숫자로만 가능합니다.", Toast.LENGTH_SHORT).show();
                }else if(userPassword.length()<8||!userPassword.equals(userCheckPassword)){
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
                ) {
                    Toast.makeText(getApplicationContext(), "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }else if(!Pattern.matches("^[a-zA-Z가-힣]+$",userName)){
                    Toast.makeText(getApplicationContext(), "이름 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (userPhone.length()>11||!Pattern.matches("^[0-9]*$",userPhone)) {
                    Toast.makeText(getApplicationContext(), "전화번호 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(userId, userPassword, userName, userEmail, userPhone);
                    // 1. register (w/ created token(init))
                    try {

                        String res = user.register(); // 실제 register가 일어나는 부분 최원석
                        System.out.println(res);
                        if (res.equals("success")) {
                            Toast.makeText(getApplicationContext(), "회원가입에 성공했어요. 로그인 해주세요", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), user.getRegisterMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                }
                // check the empty values!
            }
        });
    }

}