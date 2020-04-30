package com.example.athome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private static NaverMap nm = null;
    private Button enrollBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(tb);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        enrollBtn = (Button) findViewById(R.id.enroll_button);
        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),enrollActivity.class);
                startActivity(intent);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();
                if (id == R.id.notice) {
                    Toast.makeText(getApplicationContext(), "공지사항", Toast.LENGTH_LONG).show();
                } else if (id == R.id.reserinfo) {
                    Toast.makeText(getApplicationContext(), "예약내역", Toast.LENGTH_LONG).show();
                } else if (id == R.id.payment) {
                    Toast.makeText(getApplicationContext(), "결제•충전•적립", Toast.LENGTH_LONG).show();
                }else if (id == R.id.account) {
                    Toast.makeText(getApplicationContext(), "계정관리", Toast.LENGTH_LONG).show();

                }else if (id == R.id.setting) {
                    Toast.makeText(getApplicationContext(), "설정", Toast.LENGTH_LONG).show();
                }
                return true;
            }

        });

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_view, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
    }




    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        nm = naverMap;
        naverMap.setMapType(NaverMap.MapType.Navi);
        Marker marker[] = new Marker[2];
        for(int i=0;i<2;i++) {
            marker[i] = new Marker();
        }
        marker[0].setPosition(new LatLng(37.5670135, 126.9783740));
        marker[1].setPosition(new LatLng(40.5670135, 126.9783740));
        marker[0].setMap(naverMap);
        marker[1].setMap(nm);


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

    public void loginButtonClicked(View v){
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        //인텐트 실행
        startActivity(intent);
    }
}