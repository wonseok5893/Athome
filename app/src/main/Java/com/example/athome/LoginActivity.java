package com.example.athome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button btn_register, loginButton;
    private Button btn_back;
    private EditText et_id, et_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        loginButton = findViewById(R.id.loginButton);

        //로그인 버튼 클릭시
        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = et_id.getText().toString();
                String userPassword = et_pw.getText().toString();
                User user = new User(userId, userPassword);
                try {
                    String loginResult = user.login(); //로그인이 일어나는 부분임

                    SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();
                    String sharedToken = sf.getString("token", "");//shared파일에서 key="token"가져오기
                    System.out.println(sharedToken);

                    if (loginResult.equals("success")) {
                        if(sharedToken=="") {//저장된 토큰 값 없을시 새로 생성 후 저장
                            editor.putString("token", user.getToken());
                            editor.commit();
                        }
                        else{//저장된 토큰값이 있을시 지우고 새로 생성후 저장
                            editor.remove("token");
                            editor.putString("token", user.getToken());
                            editor.commit();
                        }

                    Intent intent = new Intent(getApplicationContext(), com.example.athome.main.MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
                    else{
                        Toast.makeText(getApplicationContext(),  "로그인 실패", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

        });

        //회원가입 버튼 클릭시
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_back = (Button) findViewById(R.id.btn_login_cancel);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }
        );


    }
}
