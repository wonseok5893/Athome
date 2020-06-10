package com.example.athome;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PurposeActivity extends AppCompatActivity {
    private Button backBtn,saveBtn;
    private TextView purpose_list_text;
    private CheckBox purpose1, purpose2, purpose3, purpose4, purpose5, purpose6, purpose7;
    private EditText purpose_other;
    String other = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);
        this.Initialize();
        this.SetLisener();
        Intent intent = getIntent();
    }

    public void Initialize() {
        backBtn = (Button) findViewById(R.id.purpose_backBtn);
        saveBtn = (Button)findViewById(R.id.purpose_save);
        purpose_list_text = (TextView) findViewById(R.id.purpose_list_text);
        purpose1 = (CheckBox) findViewById(R.id.purpose1);
        purpose2 = (CheckBox) findViewById(R.id.purpose2);
        purpose3 = (CheckBox) findViewById(R.id.purpose3);
        purpose4 = (CheckBox) findViewById(R.id.purpose4);
        purpose5 = (CheckBox) findViewById(R.id.purpose5);
        purpose6 = (CheckBox) findViewById(R.id.purpose6);
        purpose7 = (CheckBox) findViewById(R.id.purpose7);
        purpose_other = (EditText) findViewById(R.id.purpose_other);
    }

    public void SetLisener()
    {
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.purpose1:
                        break;
                    case R.id.purpose2:
                        break;
                    case R.id.purpose3:
                        break;
                    case R.id.purpose4:
                        break;
                    case R.id.purpose5:
                        break;
                    case R.id.purpose6:
                        break;
                    case R.id.purpose7:
                        if(purpose_other.getVisibility() == v.INVISIBLE) {
                            purpose_other.setVisibility(v.VISIBLE);
                        } else {
                            purpose_other.setVisibility(v.INVISIBLE);
                        }
                        String other = purpose_other.getText().toString();
                        break;
                    case R.id.purpose_backBtn:
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.purpose_save:
                        Toast.makeText(getApplicationContext(), "방문목적이 선택되었습니다.", Toast.LENGTH_LONG).show();

                }
            }
        };
        backBtn.setOnClickListener(Listener);
        saveBtn.setOnClickListener(Listener);
        purpose_other.setOnClickListener(Listener);
        purpose7.setOnClickListener(Listener);

    }


}
