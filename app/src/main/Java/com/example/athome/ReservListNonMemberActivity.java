package com.example.athome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.reservation_list.ItemNowReservData;
import com.example.athome.reservation_list.NowReservListAdapter;
import com.example.athome.reservation_list.NowReservTicket;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.MarkerResult;
import com.example.athome.retrofit.nonUserReserveData;
import com.example.athome.retrofit.nonUserReserveResult;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;

public class ReservListNonMemberActivity extends AppCompatActivity {
    private EditText non_member_phone_edit;
    private Button btn_back_reserv_list_non_member, btn_non_member_look_up;
    private ListView nonUserListView;

    private nonUserReserveResult result;

    private ArrayList<ItemNowReservData> data = null;
    private NowReservListAdapter adapter;

    private ArrayList<nonUserReserveData> reserveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_list_non_member);

        nonUserListView = findViewById(R.id.nonUserListView);
        non_member_phone_edit = findViewById(R.id.non_member_phone_edit);
        btn_back_reserv_list_non_member = (Button) findViewById(R.id.btn_back_reserv_list_non_member);
        btn_back_reserv_list_non_member.setOnClickListener(new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View v) {
                                                                   finish();
                                                               }
                                                           }
        );

        btn_non_member_look_up = (Button) findViewById(R.id.btn_non_member_look_up);
        btn_non_member_look_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                String sharedToken = sf.getString("token", "");

                ApiService serviceApi = new RestRequestHelper().getApiService();
                final Call<nonUserReserveResult> res = serviceApi.requestReserveList(non_member_phone_edit.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = res.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (result != null) {
                    if (result.getResult().equals("success")) {
                        reserveList = (ArrayList<nonUserReserveData>) result.getData();
                        setList(reserveList);
                    } else {
                        Toast.makeText(ReservListNonMemberActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        if(data != null) {
                            data.clear();
                        }
                        adapter = new NowReservListAdapter(ReservListNonMemberActivity.this, R.layout.now_reserv_listview_item, data);
                        nonUserListView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(ReservListNonMemberActivity.this, "전화번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    if(data != null) {
                        data.clear();
                    }
                    adapter = new NowReservListAdapter(ReservListNonMemberActivity.this, R.layout.now_reserv_listview_item, data);
                    nonUserListView.setAdapter(adapter);
                }


            }
        });

    }

    void setList(ArrayList<nonUserReserveData> reserveList) {
        data = new ArrayList<>();
        if (reserveList != null) {
            ItemNowReservData tmp;
            String StartTime;
            String EndTime;
            for (int i = 0; i < reserveList.size(); i++) {
                StartTime = reserveList.get(i).getStartTime();
                String a = StartTime.substring(0, 20);
                String b = StartTime.substring(30);
                StartTime = a + b;

                EndTime = reserveList.get(i).getEndTime();
                a = EndTime.substring(0, 20);
                b = EndTime.substring(30);
                EndTime = a + b;

                SimpleDateFormat sdfToDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
                final SimpleDateFormat sdfDate = new SimpleDateFormat("yy-MM-dd");
                final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                try {
                    final Date nowReserveStart = sdfToDate.parse(StartTime);
                    final Date nowReserveEnd = sdfToDate.parse(EndTime);
                    final String nowReserveStartDate = sdfDate.format(nowReserveStart);
                    final String nowReserveEndDate = sdfDate.format(nowReserveEnd);
                    final String nowReserveStartTime = sdfTime.format(nowReserveStart);
                    final String nowReserveEndTime = sdfTime.format(nowReserveEnd);
                    final String nowReserveCarNumber = reserveList.get(i).getCarNumber();
                    final String nowReserveParkingNumber = reserveList.get(i).getLocation().getParkingInfo();
                    final String nowReserveState;
                    Date now = new Date();
                    SimpleDateFormat sdfNow = new SimpleDateFormat("MM dd HH:mm");
                    String nowTime = sdfNow.format(now);
                    now = sdfNow.parse(nowTime);

                    String StartTemp = sdfNow.format(nowReserveStart);
                    Date Start = sdfNow.parse(StartTemp);

                    if (now.getTime() < Start.getTime()) {
                        nowReserveState = "대기 중";
                    } else {
                        nowReserveState = "주차 중";
                    }
                    tmp = new ItemNowReservData(nowReserveStartDate, nowReserveEndDate, nowReserveStartTime, nowReserveEndTime
                            , nowReserveCarNumber, nowReserveParkingNumber, nowReserveState);
                    data.add(tmp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        //리스트 속의 아이템 연결
        adapter = new NowReservListAdapter(this, R.layout.now_reserv_listview_item, data);
        nonUserListView.setAdapter(adapter);


        nonUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(ReservListNonMemberActivity.this, NowReservTicket.class);
                Toast.makeText(ReservListNonMemberActivity.this, "기다려봐 아직 미 구현", Toast.LENGTH_SHORT).show();
            }
        });


    }
}


