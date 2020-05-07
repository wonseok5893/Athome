package com.example.athome.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.athome.DetailsActivity;
import com.example.athome.GpsTracker;
import com.example.athome.LoginActivity;
import com.example.athome.R;
import com.example.athome.User;
import com.example.athome.account.AccountActivity;
import com.example.athome.enrollActivity;
import com.example.athome.notice.NoticeActivity;
import com.example.athome.reservation_list.ReservListActivity;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private SearchView searchView;
    private GpsTracker gpsTracker;
    private static NaverMap nm = null;
    private Button enrollBtn;
    private TextView name, id, point, profile;
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ****** 아래 토큰 확인하는 if문에서 user = new User() 해주면 오류남 조건이 거짓일 경우 user가 new되지 않기 때문인 것으로 보임.
           oncreate될때 빈 user객체 생성하고 토큰확인하면 유저정보 set해주고, 토큰 없으면 그냥 빈 상태로 놔뒀다고 로그인시 유저정보 set해주는 방향으로 해주는게 좋지 않을까 함*/
        user = new User();

        /*저장된 토큰 가져오기*/
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
//      SharedPreferences.Editor editor = sf.edit(); //토큰 업데이트 삭제에서 쓸거
        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기
        System.out.println(sharedToken);
        if (sharedToken != "") {
//            user = new User();
            //검증
            if (user.authenticate(sharedToken)) {
                Toast.makeText(getApplicationContext(), user.getUserId() + " 님 어서오세요!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), user.getAuthMessage() + "", Toast.LENGTH_SHORT).show();
            }
        }

        //상단바 설정
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //전체화면 설정
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //네비게이션화면설정
        NavigationView navigationView = (NavigationView) findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);//리스너설정

        if (user.getUserId() == null) { // 로그인 되지 않은 경우

            navigationView.inflateHeaderView(R.layout.before_login_navi_header);
            navigationView.inflateMenu(R.menu.navi_menu);
        } else { // 로그인 되지 않은 경우
            navigationView.inflateHeaderView(R.layout.after_login_navi_header);
            View header = navigationView.getHeaderView(0);
            name = (TextView) header.findViewById(R.id.navi_user_name);
            id = (TextView) header.findViewById(R.id.navi_user_id);
            point = (TextView) header.findViewById(R.id.navi_user_point);

            name.setText(user.getUserName());
            id.setText(user.getUserId());
            point.setText(user.getUserPoint() + "");
            navigationView.inflateMenu(R.menu.navi_menu);
        }


        //공유AP 버튼
        enrollBtn = (Button) findViewById(R.id.enrollBtn);
        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), enrollActivity.class);
                startActivity(intent);
            }
        });

        // 맵 동기화
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_view, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);


        //서치뷰 설정
        searchView=(SearchView)findViewById(R.id.searchView);
        Typeface typeface = getResources().getFont(R.font.dreamgothic3);

        if(searchView!=null){
            int searchTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchView.findViewById(searchTextId);
            if (searchText != null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
                searchText.setTypeface(typeface);
                searchText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
            }
        }

    }

    // 맵 준비되면 onMapReady에서 naverMap객체를 받아옴
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        // 지도종류 Basic
        naverMap.setMapType(NaverMap.MapType.Basic);

        // Test용 마커 생성 후 지도상에 set
        SharePlace test = new SharePlace(37.763695, 126.9783740);
        test.getMyMarker().setMap(naverMap);
        test.getMyMarker().setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        final Marker marker[] = new Marker[2];
        InfoWindow infoWindow = new InfoWindow();
        // test : maker onclick 시 네비게이션 (윤지원 04-30)

        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(context) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "정보 창 내용";
            }
        });


        for (int i = 0; i < 2; i++) {
            marker[i] = new Marker();
            marker[i].setWidth(50);  //마커 사이즈 수정 가능
            marker[i].setHeight(80);

        }
        marker[0].setCaptionText("여길..해보지");
        marker[0].setCaptionRequestedWidth(200);
        marker[0].setPosition(new LatLng(37.5670135, 126.9783740));
        infoWindow.setPosition(new LatLng(37.5670135, 126.9783740));
        marker[1].setPosition(new LatLng(40.5670135, 126.9783740));


        marker[0].setMap(naverMap);
        marker[1].setMap(nm);

        //kakao navi 연동 부분
        //클릭시 marker[0] 위치 DetailActivity intent로 전달 (윤지원)
        marker[0].setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                LatLng position = marker[0].getPosition();
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                return true;
            }
        });
//
//        nm.setOnMapClickListener(new NaverMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
//                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
//                startActivity(intent);
//            }
//        }); //마커클릭시 세부사항 나오게 바꿔야함. . .  .
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }

        }
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);

        int id = menuItem.getItemId();
        String title = menuItem.getTitle().toString();
        if (id == R.id.notice) {//공지사항
            Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);//화면전환시효과
        } else if (id == R.id.reserinfo) {//예약내역
            Intent intent = new Intent(getApplicationContext(), ReservListActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
        } else if (id == R.id.payment) {//결제충전적립
            Toast.makeText(getApplicationContext(), "결제,충전,적립", Toast.LENGTH_LONG).show();
        } else if (id == R.id.account) {//계정관리
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
        } else if (id == R.id.setting) {//환경설정
            Toast.makeText(getApplicationContext(), "환경설정", Toast.LENGTH_LONG).show();
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override //뒤로가기 버튼 제어(로그인 후 뒤로가기 버튼 누르면 로그인하기 페이지로 이동 제어)해야함
   public void onBackPressed() {

    }

    public void loginButtonClicked(View v) {//로그인하기버튼
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        //인텐트 실행
        startActivity(intent);
    }
}

