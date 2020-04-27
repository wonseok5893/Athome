package com.example.athome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context context = this;

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
                    Toast.makeText(getApplicationContext(), "공지사항", Toast.LENGTH_LONG).show(); //Intent 처리부분
                } else if (id == R.id.reserinfo) {
                    Toast.makeText(getApplicationContext(), "예약내역", Toast.LENGTH_LONG).show();
                } else if (id == R.id.payment) {
                    Toast.makeText(getApplicationContext(), "결제,충전,적립", Toast.LENGTH_LONG).show();
                } else if (id == R.id.account) {
                    Toast.makeText(getApplicationContext(), "계정관리", Toast.LENGTH_LONG).show();
                } else if (id == R.id.setting) {
                    Toast.makeText(getApplicationContext(), "환경설정", Toast.LENGTH_LONG).show();
                }
                return true;
            }

        });
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
        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
        //인텐트 실행
        startActivity(intent);
    }
}
