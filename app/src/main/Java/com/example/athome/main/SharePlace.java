package com.example.athome.main;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.non_member.nonReserveActivity;
import com.example.athome.reservation.ReserveActivity;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.ReserveListResult;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.MarkerIcons;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;


// 각 공유공간의 정보를 가지고있는 객체
// 앱 실행시 주차 공간마다 가지고있게하려면 필요, 마커 누를때마다 실시간으로 DB에서 가져오게 하려면 구조 변경
public class SharePlace {

    private ReserveListResult reserveResult;

    private boolean activity = false;

    private ArrayList<String> startTimeList = new ArrayList<>();
    private ArrayList<String> endTimeList = new ArrayList<>();

    private String locationId;
    private String userId; // 고유번호
    private double latitude, longitude; // 좌표
    private String locationName;
    private Marker myMarker; // 마커
    private String img;

    // 미리보기 화면
    private Button space_resv;

    private ImageView preview_close, image;
    // 주차장 마커의 정보 -> 해당 주차장의 시간당 가격, 이용가능 시간, 주소
    private TextView fee, // 요금
            time, // 이용시간
            loc ; // 위치
    private Button naviBtn, resBtn;


    // 차량등록 후 관리자가 정보 확인한 뒤 승인하면 입력 정보로 공유공간 객체 생성
    public void readSharePlace(String locationId,
                               String userId,
                               double latitude,
                               double longitude,
                               String locationName,
                               final MainActivity main) {

        // 예약초기화
//        ReserveInitial();
        PriviewInitialize(main);

        this.locationId = locationId;
        this.locationName = locationName;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;

        // 마커 생성후 받아온 좌표값 이용해 마커 위치정보 세팅
        this.myMarker = new Marker();
        this.myMarker.setPosition(new LatLng(this.latitude,this.longitude));

        if(!activity) {
            myMarker.setIcon(MarkerIcons.BLACK);
            myMarker.setIconTintColor(Color.GRAY);
        }

        final Intent intent = new Intent(main.getApplicationContext(), ReserveActivity.class);
        final Intent nonuser_intent = new Intent(main.getApplicationContext(), nonReserveActivity.class);
        LatLng position = myMarker.getPosition();

        intent.putExtra("locationId", locationId);//_id
        intent.putExtra("locationName",locationName); // 주소
        intent.putExtra("userId",userId); // UserId
        intent.putExtra("latitude", latitude); // 위도
        intent.putExtra("longitude", longitude); // 경도

        nonuser_intent.putExtra("locationId", locationId);//_id
        nonuser_intent.putExtra("latitude", latitude);
        nonuser_intent.putExtra("longitude", longitude);
        nonuser_intent.putExtra("userId", userId);


        // 마커 클릭하면 이벤트 발생
        this.myMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {

                Log.d("ResTest",intent.getStringExtra("locationId"));

                    ReservationList(intent);
                    parseMsg();

                fee.setText(600 + "원/시간");
                time.setText("1시 ~ 6시");
                loc.setText(intent.getStringExtra("locationName"));


                if (main.getUser().getUserId() == null) { // 비회원일때
                    loc.setText(nonuser_intent.getStringExtra("locationId"));
                    LatLng position = myMarker.getPosition();
                    nonuser_intent.putExtra("position", position);

                    Log.d("teststs", nonuser_intent.getStringExtra("locationId"));
                    main.PreviewVisible();
                    space_resv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            main.startActivity(nonuser_intent);
                        }
                    });
                } else { // 회원일때
                    loc.setText(intent.getStringExtra("locationId"));
                    LatLng position = myMarker.getPosition();
                    intent.putExtra("position", position);

                    Log.d("teststs", intent.getStringExtra("locationId") + " " +
                            intent.getStringExtra("userId"));
                    main.PreviewVisible();
                    space_resv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            main.startActivity(intent);
                        }
                    });
                }
                return true;
            }
        });
    }

    public Marker getMyMarker() {
        return this.myMarker;
    }

    void PriviewInitialize(final MainActivity main) {

        // 예약하기 버튼
        space_resv = (Button) main.findViewById(R.id.space_resv);

        // 미리보기 비활성화 버튼
        preview_close = (ImageView)main.findViewById(R.id.preview_close);
        preview_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.PreviewInvisible();
            }
        });

        resBtn = (Button)main.findViewById(R.id.space_resv);
        naviBtn = (Button)main.findViewById(R.id.space_navi);
        time = (TextView)main.findViewById(R.id.space_time);
        fee = (TextView)main.findViewById(R.id.space_fee);
        loc = (TextView)main.findViewById(R.id.space_loc);
    }

    void ReservationList(Intent intent){


        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<ReserveListResult> res = serviceApi.getReserveData(intent.getStringExtra("locationId"));
        Log.d("ResTest",intent.getStringExtra("locationId"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reserveResult = res.execute().body();
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
        if (reserveResult == null) {
            Log.i("junggyu", "예약사항없음 or 실패 으앙!");
        } else {
            int reservCount = reserveResult.getReservationList().size();

            for (int i = 0; i < reservCount; i++) {
                String s = reserveResult.getReservationList().get(i).getStartTime();
                String e = reserveResult.getReservationList().get(i).getEndTime();
                startTimeList.add(s);
                endTimeList.add(e);
            }
        }
    }

    void parseMsg(){


        for(int i=0;i<startTimeList.size();i++) {

            Log.d("junggyu", startTimeList.get(i));
            Log.d("junggyu", endTimeList.get(i));
        }
    }



/*
    void ReserveInitial() {


            서버에서 받아와야 할 값
            1. 몇시부터 몇시까지 공유하는지
            2. 어느요일에 공유하는지


        // 파싱해서 여기다 값 대입해주기
        this.todayOn = todayOn;
        this.shareTime = shareTime;
        this.week = week;

        // 오늘 날짜가 공유하는 테이블인지 확인
        switch (String.valueOf(doDayOfWeek())) { // 해당 요일의 week의 값이 true이면 activate를 활성화시킴..
            case "일요일":
                if (this.week[0])
                    this.todayOn = true;
                break;
            case "월요일":
                if (this.week[1])
                    this.todayOn = true;
                break;
            case "화요일":
                if (this.week[2])
                    this.todayOn = true;
                break;
            case "수요일":
                if (this.week[3])
                    this.todayOn = true;
                break;
            case "목요일":
                if (this.week[4])
                    this.todayOn = true;
                break;
            case "금요일":
                if (this.week[5])
                    this.todayOn = true;
                break;
            case "토요일":
                if (this.week[6])
                    this.todayOn = true;
                break;
        }

        if(this.todayOn) { // todayOnOff가 true이고, 현재 시각에 예약이 없으면
            this.activity = true;
        }

        // 예약현황 참고해서 마커색깔 수정
        // 예약테이블 받아와서 바에다가 시각적인 정보 게종

    }
*/

    public boolean getActivity() {
        return this.activity;
    }

    private String doDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        String strWeek = null;

        int nWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (nWeek == 1) {
            strWeek = "일요일";
        } else if (nWeek == 2) {
            strWeek = "월요일";
        } else if (nWeek == 3) {
            strWeek = "화요일";
        } else if (nWeek == 4) {
            strWeek = "수요일";
        } else if (nWeek == 5) {
            strWeek = "목요일";
        } else if (nWeek == 6) {
            strWeek = "금요일";
        } else if (nWeek == 7) {
            strWeek = "토요일";
        }

        return strWeek;
    }

}
