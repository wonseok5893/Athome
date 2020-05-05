package com.example.athome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.athome.main.MainActivity;
import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
import com.naver.maps.geometry.LatLng;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Button resBtn,naviBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Intent 에서 marker[0] 위치 받아와 가공 (윤지원)
        Intent detail_intent = getIntent();
        LatLng position = detail_intent.getParcelableExtra("position");
        final double longtitude = position.longitude;
        final double latitude = position.latitude;

        resBtn = (Button) findViewById(R.id.de_res);
        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReserveActivity.class);
                startActivity(intent);
            }
        });

        naviBtn = (Button)findViewById(R.id.de_navi);
        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파라미터 1.도착 장소 (장소 이름, 위도, 경도) (윤지원)
                Location destination = Location.newBuilder("몰라 시발", longtitude,latitude).build();

                //파라미터 2. 세부 옵션 도착지, 1종, 빠른 경로 , 경유지 없음, (윤지원)
                KakaoNaviParams params = KakaoNaviParams.newBuilder(destination)
                        .setNaviOptions(NaviOptions.newBuilder()
                                .setCoordType(CoordType.WGS84)
                                .build())
                        .build();
                //kakao navi 실행 현재 액티비지 (DetailActivity) context , params 입력 (윤지원)
                KakaoNaviService.getInstance().shareDestination(DetailsActivity.this, params);
            }
        });
    }
}
