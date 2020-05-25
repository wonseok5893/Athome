package com.example.athome.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.example.athome.GpsTracker;
import com.example.athome.LoginActivity;
import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.User;
import com.example.athome.account.AccountActivity;
import com.example.athome.admin.UsersListActivity;
import com.example.athome.admin_enroll.AdminCarlistActivity;
import com.example.athome.admin_notice.AdminNoticeActivity;
import com.example.athome.admin_enroll.AdminEnrollActivity;
import com.example.athome.notice.NoticeActivity;
import com.example.athome.notification.NotificationActivity;
import com.example.athome.point_charge.PointChargeActivity;
import com.example.athome.reservation_list.ReservListActivity;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.MarkerResult;
import com.example.athome.setting.SettingActivity;
import com.example.athome.shared_parking.MySharedParkingActivity;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.Symbol;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener {


    // 현재위치 관련
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private GpsTracker gpsTracker;
    private static NaverMap nm;
    private Button enrollBtn;
    private Button btn_back_enroll;
    private TextView name, id, point, profile;
    private User user;
    private Button loginButton,btn_notification_box,btn_point_charge;
    private EditText searchEditText; // 웹뷰 띄우는 창
    private Button btn_search;
    private MarkerResult markerResult;
    private LinearLayout preview;
    private Animation slide_up,slide_down,stay;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preview = (LinearLayout) findViewById(R.id.preview);
        preview.setVisibility(View.INVISIBLE);
        slide_down = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        slide_up = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        stay = AnimationUtils.loadAnimation(this,R.anim.stay);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);




        /* ****** 아래 토큰 확인하는 if문에서 user = new User() 해주면 오류남 조건이 거짓일 경우 user가 new되지 않기 때문인 것으로 보임.
           oncreate될때 빈 user객체 생성하고 토큰확인하면 유저정보 set해주고, 토큰 없으면 그냥 빈 상태로 놔뒀다고 로그인시 유저정보 set해주는 방향으로 해주는게 좋지 않을까 함*/
        user = new User();

        /*저장된 토큰 가져오기*/
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit(); //토큰 업데이트 삭제에서 쓸거
        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기
        System.out.println(sharedToken);
        if (sharedToken != "") {
            //검증
            if (user.authenticate(sharedToken)) {
                Toast.makeText(getApplicationContext(), user.getUserId() + " 님 어서오세요!", Toast.LENGTH_SHORT).show();
            } else {
                // 토큰 오류시 User 초기화
                editor.remove("token");
                editor.commit();
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
            View header = navigationView.getHeaderView(0);
            loginButton = (Button) header.findViewById(R.id.login_button);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    //인텐트 실행
                    startActivity(intent);
                }
            });
            navigationView.inflateMenu(R.menu.navi_menu);
        } else { // 로그인 된경우
            navigationView.inflateHeaderView(R.layout.after_login_navi_header);
            View header = navigationView.getHeaderView(0);
            name = (TextView) header.findViewById(R.id.navi_user_name);
            id = (TextView) header.findViewById(R.id.navi_user_id);
            point = (TextView) header.findViewById(R.id.navi_user_point);
            btn_notification_box=(Button)header.findViewById(R.id.btn_notification_box);
            btn_point_charge=(Button)header.findViewById(R.id.btn_point_charge) ;

            name.setText(user.getUserName());
            id.setText(user.getUserId());
            point.setText(user.getUserPoint() + "");
            if (user.getUserState() != 1)// 관리자 메뉴
                navigationView.inflateMenu(R.menu.navi_menu);
            else
                navigationView.inflateMenu(R.menu.admin_menu);


            //알림함 레이아웃
            btn_notification_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                }
            });

            //포인트 충전하기 레이아웃
            btn_point_charge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PointChargeActivity.class);
                    startActivity(intent);
                }
            });
        }


        // 맵 동기화
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_view, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        final Geocoder geocoder = new Geocoder(this);

        searchEditText = findViewById(R.id.search_view);
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
//                resultAddress.setText("롯데리아 수원화성DT점");
            }
        });

        //주소 검색 버튼 클릭
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Address> list = null;

                try {
                    list = geocoder.getFromLocationName(searchEditText.getText().toString(), 10); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        Log.e("test", "주소없음");
                        Toast.makeText(getApplicationContext(), "주소를 찾을 수 없습니다.", Toast.LENGTH_LONG).show();
                    } else {

                        for (Address l : list) {
                            Log.e("test", list.get(0).toString());
                        }

                        double lat = list.get(0).getLatitude();
                        double longti = list.get(0).getLongitude();

                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, longti));
                        nm.moveCamera(cameraUpdate);
                    }
                }

            }
        });


        enrollBtn = (Button) findViewById(R.id.enrollBtn);
        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getUserId() == null){
                    Toast.makeText(context, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Intent intent = new Intent(getApplicationContext(), MySharedParkingActivity.class);
                Toast.makeText(getApplicationContext(), "주차장 공유 기능이 활성화되었습니다.", Toast.LENGTH_LONG).show();

                nm.setOnMapLongClickListener(new NaverMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull PointF point, @NonNull LatLng coord) {

                        Log.d("test", "현재 좌표 : " + coord.latitude + ", " + coord.longitude);

                        List<Address> list = null;
                        String locationName = null;
                        Log.d("test", "실행체크 1번");

                        try {
                            list = geocoder.getFromLocation((double) coord.latitude, (double) coord.longitude, 1); // 얻어올 값의 개수
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                        }
                        if (list != null) {
                            if (list.size() == 0) {
                                Log.e("test", "이상한 장소입니다.");
                                Toast.makeText(MainActivity.this, "위치를 다시 지정해주십시오.", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                locationName = list.get(0).getAddressLine(0);
                                Log.d("test", locationName);
                            }
                        }

                        intent.putExtra("SelectLocation", new LatLng(coord.latitude, coord.longitude));
                        intent.putExtra("LocationName", locationName);
                        intent.putExtra("User",user);
                        startActivity(intent);
                    }
                });


//                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    // 맵 준비되면 onMapReady에서 naverMap객체를 받아옴
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        nm = naverMap;
        nm.setLocationSource(locationSource);
        nm.setLocationTrackingMode(LocationTrackingMode.Follow);

        UiSettings uiSettings = nm.getUiSettings();
        // 지도종류 Basic
        nm.setMapType(NaverMap.MapType.Basic);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.5666102, 126.9783881))
                .animate(CameraAnimation.Easing);
        nm.moveCamera(cameraUpdate);

        uiSettings.setLocationButtonEnabled(true);




        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");

        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<MarkerResult> res = serviceApi.getMarkerData(sharedToken, "catstone");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    markerResult = res.execute().body();
                    if(markerResult == null){
                        return;
                    }
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
        if(markerResult == null) {
            Log.i("jiwon","실패");
        }else{
            Log.i("jiwon","성공");
        }

        int markerCount = markerResult.getData().size();

        ArrayList<SharePlace> placeList = new ArrayList<>();

        for(int i=0;i<markerCount;i++) {
            SharePlace s = new SharePlace();
            s.readSharePlace(markerResult.getData().get(i).getId()
                    ,markerResult.getData().get(i).getOwner().getUserId(),
                    Double.parseDouble(markerResult.getData().get(i).getLatitude()),
                    Double.parseDouble(markerResult.getData().get(i).getLongitude()),
                    this);
            placeList.add(s);
        }

        for(SharePlace s : placeList) {
            s.getMyMarker().setMap(nm);
        }



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
            intent.putExtra("user", user);
            startActivity(intent);
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
        } else if (id == R.id.setting) {//환경설정
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
        }else if(id==R.id.admin_notice) {// 공지사항 관리
            Intent intent = new Intent(getApplicationContext(), AdminNoticeActivity.class);
            startActivity(intent);
        }else if(id==R.id.admin_shared_enroll) { //관리자 배정자 등록
            Intent intent = new Intent(getApplicationContext(), AdminEnrollActivity.class);
            startActivity(intent);
        }else if(id==R.id.admin_car_enroll) { // 관리자 차량 등록
            Intent intent = new Intent(getApplicationContext(), AdminCarlistActivity.class);
            startActivity(intent);
        }else if(id==R.id.admin_users) { // 사용자 관리
            Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
            startActivity(intent);
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override //뒤로가기 버튼 제어(로그인 후 뒤로가기 버튼 누르면 로그인하기 페이지로 이동 제어)해야함
    public void onBackPressed() {

    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode){

            case SEARCH_ADDRESS_ACTIVITY:

                if(resultCode == 1){

                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        searchEditText.setText(data);

                }
                break;

        }

    }
    public User getUser() {
        return user;
    }

    public void PreviewVisible() {
        this.preview.setVisibility(View.VISIBLE);
        overridePendingTransition(R.anim.slide_up, R.anim.stay);
        preview.startAnimation(slide_up);

    }
    public void PreviewInvisible(){
        this.preview.setVisibility(View.INVISIBLE);
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
        preview.startAnimation(slide_down);
    }
}

