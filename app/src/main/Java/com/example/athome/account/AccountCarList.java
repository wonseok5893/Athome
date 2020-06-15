package com.example.athome.account;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.EditCarNumberResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;


public class AccountCarList extends Activity {
    private Button btn_back_car_list;
    private FloatingActionButton btn_car_register;
    private ListView car_listView;
    private ArrayList<ItemAccountCarData> data = null;
    private TextView car_number_delete;
    private CarListAdapter adapter;
    private ArrayList<String> userCarNumber = null;
    private EditCarNumberResult editRes;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_car_list);

        this.InitializeView();
        this.SetListner();


        ArrayList<ItemAccountCarData> temp = new ArrayList<>();
        if (userCarNumber != null) {
            for (int i = 0; i < userCarNumber.size(); i++) {
                ItemAccountCarData tmp = new ItemAccountCarData(userCarNumber.get(i));
                temp.add(tmp);
            }
            for (int i = 0; i < temp.size(); i++) {
                data.add(temp.get(i));
            }
        }

        //리스트 속의 아이템 연결
        adapter = new CarListAdapter(this, R.layout.account_car_listview_item, data);
        car_listView.setAdapter(adapter);

        car_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(AccountCarList.this)
                        .setTitle("차량번호 삭제")
                        .setMessage("해당 차량번호를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                                String sharedToken = sf.getString("token", "");

                                ApiService serviceApi = new RestRequestHelper().getApiService();
                                final Call<EditCarNumberResult> res = serviceApi.deleteCarNumber(sharedToken, userCarNumber.get(position));

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            editRes = res.execute().body();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (editRes != null) {
                                    if (editRes.getResult().equals("success")) {
                                        Toast.makeText(AccountCarList.this, editRes.getMessage(), Toast.LENGTH_SHORT).show();
                                        AccountCarList.this.data.remove(position);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(AccountCarList.this, editRes.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AccountCarList.this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();

                return;
            }
        });
    }



    public void InitializeView() {
        btn_back_car_list = (Button) findViewById(R.id.btn_back_car_list);
        btn_car_register = (FloatingActionButton) findViewById(R.id.btn_car_register);
        car_listView = (ListView) findViewById(R.id.car_listView);
        data = new ArrayList<>();
        Intent intent = new Intent();
        userCarNumber = intent.getStringArrayListExtra("textUserCarNumber");
    }

    public void SetListner() {
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_back_car_list: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
                        break;
                    case R.id.btn_car_register: //등록하기  버튼
                        // 다이얼로그를 생성. 사용자가 만든 클래스이다.
                        AccountCarRegister accountCarRegister = new AccountCarRegister(AccountCarList.this);

                        // 다이얼로그 호출
                        //그외 작업
                        accountCarRegister.callFunction();
                        accountCarRegister.setDialogResult(new AccountCarRegister.OnMyDialogResult() {
                            @Override
                            public void finish(String result) {
                                ItemAccountCarData tmp = new ItemAccountCarData(result);
                                data.add(tmp);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        break;
                }
            }
        };
        btn_back_car_list.setOnClickListener(Listener);
        btn_car_register.setOnClickListener(Listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }
}