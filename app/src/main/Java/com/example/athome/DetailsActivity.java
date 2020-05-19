package com.example.athome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
import com.naver.maps.geometry.LatLng;

public class DetailsActivity extends AppCompatActivity {

    private Button resBtn,naviBtn;
    private TextView de_location,
            de_fee,
            de_time,
            de_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView de_location = (TextView)findViewById(R.id.de_location);
        TextView de_fee = (TextView)findViewById(R.id.de_fee);
        TextView de_time = (TextView)findViewById(R.id.de_time);
        TextView de_call = (TextView)findViewById(R.id.de_call);

        // Intent 에서 marker[0] 위치 받아와 가공 (윤지원)
        final Intent detail_intent = getIntent();
        de_location.setText(detail_intent.getStringExtra("locationId"));
        de_fee.setText(detail_intent.getStringExtra("userId"));
        de_time.setText(Double.toString(detail_intent.getDoubleExtra("latitude",0)));
        de_call.setText(Double.toString(detail_intent.getDoubleExtra("longitude",0)));

        final Intent intent = new Intent(getApplicationContext(),ReserveActivity.class);
        intent.putExtra("locationId", detail_intent.getStringExtra("locationId"));

        resBtn = (Button) findViewById(R.id.de_res);
        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        naviBtn = (Button)findViewById(R.id.de_navi);
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
                KakaoNaviService.getInstance().shareDestination(DetailsActivity.this, params);
            }
        });
    }
}
