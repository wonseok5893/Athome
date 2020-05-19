package com.example.athome.main;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.athome.DetailsActivity;
import com.example.athome.DrawerActivity;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

// 각 공유공간의 정보를 가지고있는 객체
// 앱 실행시 주차 공간마다 가지고있게하려면 필요, 마커 누를때마다 실시간으로 DB에서 가져오게 하려면 구조 변경
public class SharePlace {

    private String locationId;
    private String userId; // 고유번호
    private double latitude, longitude; // 좌표
    private Marker myMarker; // 마커
    private String img;



    // 차량등록 후 관리자가 정보 확인한 뒤 승인하면 입력 정보로 공유공간 객체 생성
    public void readSharePlace(String locationId,
                               String userId,
                               double latitude,
                               double longitude,
                               final MainActivity main) {
        this.locationId = locationId;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;

        // 마커 생성후 받아온 좌표값 이용해 마커 위치정보 세팅
        this.myMarker = new Marker();
        this.myMarker.setPosition(new LatLng(this.latitude,this.longitude));


        final Intent intent = new Intent(main.getApplicationContext(), DrawerActivity.class);
        LatLng position = myMarker.getPosition();

        intent.putExtra("locationId", locationId);
        intent.putExtra("userId",userId);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);

        // 마커 클릭하면 이벤트 발생
        this.myMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                LatLng position = myMarker.getPosition();
                intent.putExtra("position", position);
                main.startActivity(intent);
                return true;
            }
        });
    }

    public Marker getMyMarker() {
        return this.myMarker;
    }
}
