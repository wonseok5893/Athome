package com.example.athome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class enrollActivity extends AppCompatActivity {
    private Button btn_back;
    private Button btn_register;
    private EditText en_phonenum,en_birth,en_carnum,en_location;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        en_phonenum = findViewById(R.id.en_phonenum);
        en_birth = findViewById(R.id.en_birth);
        en_location = findViewById(R.id.en_location);
        en_carnum=findViewById(R.id.en_carnum);

        btn_back=(Button)findViewById(R.id.en_back);
        btn_register=(Button)findViewById(R.id.en_enroll);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = en_phonenum.getText().toString();
                String userBirth = en_birth.getText().toString();
                String userCar = en_carnum.getText().toString();
                String userAddress = en_location.getText().toString();

                if(userAddress.isEmpty()||userBirth.isEmpty()||userCar.isEmpty()||userPhone.isEmpty()){
                    Toast.makeText(getApplicationContext(), "모든 항목을 기입하십시오.", Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent(getApplicationContext(), enroll_ImgActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }


            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

