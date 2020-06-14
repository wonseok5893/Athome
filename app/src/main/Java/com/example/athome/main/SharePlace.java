package com.example.athome.main;

import android.content.Context;
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
import com.example.athome.retrofit.LocationInfoList;
import com.example.athome.retrofit.ReserveListResult;
import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
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

    private LocationInfoList locationInfoList;
    String locationStartTime;
    String locationEndTime;
    ArrayList<Integer> locationDaySet = new ArrayList<>();


    private String locationId;
    private String userId; // 고유번호
    private double latitude, longitude; // 좌표
    private String locationName;
    private Marker myMarker; // 마커
    private Context context;
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
                               final double latitude,
                               final double longitude,
                               final String locationName,
                               final MainActivity main,
                               final Context context) {

        PriviewInitialize(main);
        this.locationId = locationId;
        this.locationName = locationName;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.context = context;

        // 마커 생성후 받아온 좌표값 이용해 마커 위치정보 세팅
        this.myMarker = new Marker();
        this.myMarker.setPosition(new LatLng(this.latitude,this.longitude));

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

                initLocationInfo(intent);
                Log.d("ResTest",intent.getStringExtra("locationId"));

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

        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파라미터 1.도착 장소 (장소 이름, 위도, 경도) (윤지원)
                Location destination = Location.newBuilder(locationName,longitude,latitude).build();

                //파라미터 2. 세부 옵션 도착지, 1종, 빠른 경로 , 경유지 없음, (윤지원)
                KakaoNaviParams params = KakaoNaviParams.newBuilder(destination)
                        .setNaviOptions(NaviOptions.newBuilder()
                                .setCoordType(CoordType.WGS84)
                                .build())
                        .build();
                //kakao navi 실행 현재 액티비지 (DetailActivity) context , params 입력 (윤지원)
                KakaoNaviService.getInstance().shareDestination(context, params);
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

    void initLocationInfo(Intent intent) {

        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<LocationInfoList> res = serviceApi.getLocationInfo(locationId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    locationInfoList = res.execute().body();
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

        this.locationStartTime = locationInfoList.getLocationInfo().getPossibleStartTime();
        this.locationEndTime = locationInfoList.getLocationInfo().getPossibleEndTime();
        this.locationDaySet = (ArrayList<Integer>) locationInfoList.getLocationInfo().getTimeState();

        int timeArray[] = new int[7];

        Log.d("junggyu", "시작시간 : " + locationStartTime + ", 종료시간 : " + locationEndTime);
        for(int i=0;i<locationDaySet.size();i++) {
            timeArray[i] = locationDaySet.get(i);
        }

        intent.putExtra("locationStartTime", locationStartTime);
        intent.putExtra("locationEndTime", locationEndTime);
        intent.putExtra("locationDaySet", timeArray);


    }

}

