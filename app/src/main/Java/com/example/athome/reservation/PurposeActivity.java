package com.example.athome.reservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.athome.R;
import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.PurposeResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;

public class PurposeActivity extends AppCompatActivity {

    private CheckBox[] purposeCheck = new CheckBox[7]; // {외식, 쇼핑, 출장/비즈니스, 친구/친지방문, 의료/건강, 여행/휴가, 기타}
    private String[] purpose = {"외식", "쇼핑", "출장·비즈니스", "친구·친지방문", "의료·건강", "여행·휴가", "기타"};
    private Button purpose_save, purpose_backBtn;
    private EditText purpose_other;
    private PurposeResult ps;
    private int count = 0;
    private String reservId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);

        init();
        setListener();

    }

    public void init() {

        purposeCheck[0] = (CheckBox)findViewById(R.id.purpose1);
        purposeCheck[1] = (CheckBox)findViewById(R.id.purpose2);
        purposeCheck[2] = (CheckBox)findViewById(R.id.purpose3);
        purposeCheck[3] = (CheckBox)findViewById(R.id.purpose4);
        purposeCheck[4] = (CheckBox)findViewById(R.id.purpose5);
        purposeCheck[5] = (CheckBox)findViewById(R.id.purpose6);
        purposeCheck[6] = (CheckBox)findViewById(R.id.purpose7);
        purpose_save = (Button)findViewById(R.id.purpose_save);
        purpose_backBtn = (Button)findViewById(R.id.purpose_backBtn);
        purpose_other = (EditText)findViewById(R.id.purpose_other);
        purposeCheck[6].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    purpose_other.setVisibility(View.VISIBLE);
                } else {
                    purpose_other.setVisibility(View.INVISIBLE);
                }
            }
        });

        Intent intent = getIntent();
        this.reservId = intent.getStringExtra("reservId");

    }

    public void setListener() {

        purpose_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        purpose_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNotNull()) {

                    SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                    String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기

                    ArrayList<String> purposes = new ArrayList<>();

                    for(int i=0;i<6;i++) {
                        if(purposeCheck[i].isChecked()) { // 만약에 체크되어 있으면.
                            count++;
                            purposes.add(purpose[i]);
                        }
                    }
                    if(purposeCheck[6].isChecked()) {
                        count++;
                        purposes.add(purpose[6]);
                    }

                    String[] temp = purposes.toArray(new String[count]);
                    String result = Arrays.toString(temp);
                    result = result.replace("[","");
                    result = result.replace("]","");
                    result = result.replace(", ","/");

                    ApiService serviceApi = new RestRequestHelper().getApiService();
                    final Call<PurposeResult> res = serviceApi.sendPurpose(sharedToken,reservId, result, purpose_other.getText().toString());

                    Log.d("junggyu", "체크한 것 : " + result);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ps = res.execute().body();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(!ps.getResult().equals("success")) {
                        Toast.makeText(getApplicationContext(), ps.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    Log.d("junggyu", count + "개 전송함");
                    count = 0;

                    if(sharedToken.equals("")) {
                        Toast.makeText(getApplicationContext(),  "응답 감사합니다.\n포인트를 얻으시려면 회원가입을 해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),  "감사합니다.", Toast.LENGTH_SHORT).show();

                    }

                    Intent intent = new Intent(getApplicationContext(), com.example.athome.main.MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(),  "체크를 하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean checkNotNull() { // 체크했는지 확인
        for(int i=0;i<7;i++) {
            if(purposeCheck[i].isChecked()) { // 하나라도 체크되어 있으면 true리턴
                return true;
            }
        }
        return false; // 하나도 안체크되어 있으면 false리턴
    }
}
