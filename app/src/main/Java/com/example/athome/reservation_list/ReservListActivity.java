package com.example.athome.reservation_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.athome.R;
import com.example.athome.retrofit.ReservationListResult;
import com.example.athome.retrofit.ReservationListResult_data;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ReservListActivity extends AppCompatActivity {
    private Button btn_back_reserv;
    private ViewPager mViewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_list);

        Intent intent = getIntent();
        ArrayList<ReservationListResult_data> data = intent.getParcelableArrayListExtra("data");
        String sharedToken = intent.getStringExtra("sharedToken");
        this.InitializeView();
        this.SetListner();

        final ViewPager viewPager=(ViewPager)findViewById(R.id.reserv_list_viewpager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),data,sharedToken);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    public void InitializeView() {
        btn_back_reserv = (Button) findViewById(R.id.btn_back_reserv);
        mViewPager=(ViewPager)findViewById(R.id.container);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("현재내역"));
        tabLayout.addTab(tabLayout.newTab().setText("지난내역"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_reserv: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;

                }
            }
        };
        btn_back_reserv.setOnClickListener(Listener);

    }

}
