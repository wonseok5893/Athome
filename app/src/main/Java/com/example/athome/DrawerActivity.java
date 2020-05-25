package com.example.athome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
import com.naver.maps.geometry.LatLng;

public class DrawerActivity extends AppCompatActivity {
    private ImageView image;
    private TextView fee,time,loc ;// 주차장 마커의 정보 -> 해당 주차장의 시간당 가격, 이용가능 시간, 주소
    private Button naviBtn, resBtn;
    private LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //레이아웃을 위에 겹쳐서 올리는 부분
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//레이아웃 객체생성
        view= (LinearLayout)inflater.inflate(R.layout.activity_drawer, null);

//레이아웃 위에 겹치기
        LinearLayout.LayoutParams paramll = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(paramll);
        view.setGravity(Gravity.BOTTOM);
        addContentView(view, paramll);

        // Intent 에서 marker[0] 위치 받아와 가공 (윤지원)
        final Intent detail_intent = getIntent();
        Initialize();
        Log.d("test", detail_intent.getStringExtra("locationId")+" "+
                detail_intent.getStringExtra("userId"));
        loc.setText(detail_intent.getStringExtra("locationId"));
        fee.setText(detail_intent.getStringExtra("userId"));
        time.setText(Double.toString(detail_intent.getDoubleExtra("latitude",0)));


        final Intent intent = new Intent(getApplicationContext(),ReserveActivity.class);
        intent.putExtra("locationId", detail_intent.getStringExtra("locationId"));

        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReserveActivity.class);
                startActivity(intent);
            }
        });


        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파라미터 1.도착 장소 (장소 이름, 위도, 경도) (윤지원)
                Location destination = Location.newBuilder("몰라 시발",
                        detail_intent.getDoubleExtra("latitude",0),
                        detail_intent.getDoubleExtra("longitude",0)
                ).build();

                //파라미터 2. 세부 옵션 도착지, 1종, 빠른 경로 , 경유지 없음, (윤지원)
                KakaoNaviParams params = KakaoNaviParams.newBuilder(destination)
                        .setNaviOptions(NaviOptions.newBuilder()
                                .setCoordType(CoordType.WGS84)
                                .build())
                        .build();
                //kakao navi 실행 현재 액티비지 (DetailActivity) context , params 입력 (윤지원)
                KakaoNaviService.getInstance().shareDestination(DrawerActivity.this, params);
            }
        });
    }
    private void Initialize(){


    }
}
