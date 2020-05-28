package com.example.athome.main;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.athome.R;
import com.example.athome.ReserveActivity;
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
                               final MainActivity main) {

        PriviewInitialize(main);

        this.locationId = locationId;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;

        // 마커 생성후 받아온 좌표값 이용해 마커 위치정보 세팅
        this.myMarker = new Marker();
        this.myMarker.setPosition(new LatLng(this.latitude,this.longitude));


        final Intent intent = new Intent(main.getApplicationContext(), ReserveActivity.class);
        LatLng position = myMarker.getPosition();

        intent.putExtra("locationId", locationId);//_id
        intent.putExtra("userId",userId);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);

        // 마커 클릭하면 이벤트 발생
        this.myMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {

                LatLng position = myMarker.getPosition();
                intent.putExtra("position", position);

                Log.d("teststs", intent.getStringExtra("locationId")+" "+
                        intent.getStringExtra("userId"));

                fee.setText(600 + "원/시간");
                time.setText("1시 ~ 6시");
                loc.setText(intent.getStringExtra("locationId"));

                if(main.getUser().getUserId()==null) { // 비회원일때
                    main.PreviewVisible();
                } else { // 회원일때
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
        image= (ImageView)main.findViewById(R.id.space_img);
        time = (TextView)main.findViewById(R.id.space_time);
        fee = (TextView)main.findViewById(R.id.space_fee);
        loc = (TextView)main.findViewById(R.id.space_loc);
    }
}
