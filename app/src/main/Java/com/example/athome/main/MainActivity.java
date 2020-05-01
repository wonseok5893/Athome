package com.example.athome.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.athome.LoginActivity;
import com.example.athome.R;
import com.example.athome.LoginActivity;
import com.example.athome.RestRequestHelper;
import com.example.athome.User;
import com.example.athome.notice.NoticeActivity;
import com.example.athome.reservation_list.ReservListActivity;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.AuthResult;
import com.example.athome.retrofit.LoginResult;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

import java.io.IOException;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout mDrawerLayout;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //저장된 토큰 가져오기
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
//      SharedPreferences.Editor editor = sf.edit(); //토큰 업데이트 삭제에서 쓸거
        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기
        System.out.println(sharedToken);
        if(sharedToken!="") {
            User user = new User();
            //검증
            if (user.authenticate(sharedToken)) {
                Toast.makeText(getApplicationContext(), user.getUserId() + " 님 어서오세요!", Toast.LENGTH_SHORT).show();
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

        // 맵 동기화
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_view, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

    }

    // 맵 준비되면 onMapReady에서 naverMap객체를 받아옴
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        // 지도종류 Navi로 변경
        naverMap.setMapType(NaverMap.MapType.Navi);

        // Test용 마커 생성 후 지도상에 set
        SharePlace test = new SharePlace(37.763695,126.9783740);
        test.getMyMarker().setMap(naverMap);
    }


    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
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
            case R.id.search: {
                Toast.makeText(getApplicationContext(),"검색",Toast.LENGTH_LONG).show();
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
        if (id == R.id.notice) {
            Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);//화면전환시효과
        }
        else if (id == R.id.reserinfo) {
            Intent intent = new Intent(getApplicationContext(), ReservListActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
        }
        else if (id == R.id.payment) {
            Toast.makeText(getApplicationContext(), "결제,충전,적립", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.account) {
            Toast.makeText(getApplicationContext(), "계정관리", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.setting) {
            Toast.makeText(getApplicationContext(), "환경설정", Toast.LENGTH_LONG).show();
        }
        mDrawerLayout.closeDrawers();
        return true;
    }




    public void loginButtonClicked(View v){
        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
        //인텐트 실행
        startActivity(intent);
    }
}
