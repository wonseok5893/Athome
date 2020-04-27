package com.example.athome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    private EditText sign_id, sign_pw, sign_email, sign_phone;
    private Button signUpButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sign_id = findViewById(R.id.sign_id);
        sign_pw = findViewById(R.id.sign_pw);
        sign_email = findViewById(R.id.sign_email);
        sign_phone = findViewById(R.id.sign_phone);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = sign_id.getText().toString();
                String userPassword = sign_pw.getText().toString();
                String userEmail = sign_email.getText().toString();
                String userPhone = sign_phone.getText().toString();


                if (userId.isEmpty() || userPassword.isEmpty() || userEmail.isEmpty() || userPhone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 항목을 기입하십시오.", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(userId, userPassword, userEmail, userPhone);
                    // 1. register (w/ created token(init))
                    try {
                        String res = user.register(); // 실제 register가 일어나는 부분 최원석
                        System.out.println(res);
                        if (res.equals("success")) {
                            Toast.makeText(getApplicationContext(), "회원가입에 성공했어요. 로그인 해주세요", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
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