package com.example.athome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ReservListNonMemberActivity  extends AppCompatActivity {
    private EditText non_member_phone_edit;
    private Button btn_back_reserv_list_non_member, btn_non_member_look_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_list_non_member);

        btn_back_reserv_list_non_member=(Button)findViewById(R.id.btn_back_reserv_list_non_member);
        btn_back_reserv_list_non_member.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finish();
                                        }
                                    }
        );


    }
}

