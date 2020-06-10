package com.example.athome.payment_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.athome.R;
import com.example.athome.parking_details.DetailsPagerAdapter;
import com.example.athome.reservation.ReserveActivity;
import com.google.android.material.tabs.TabLayout;

public class PaymentListActivity extends AppCompatActivity {
    private Button btn_back_payment_list;
    private ViewPager mViewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        this.InitializeView();
        this.SetListner();

        final ViewPager viewPager=(ViewPager)findViewById(R.id.payment_list_viewpager);
        final PayListPagerAdapter adapter = new  PayListPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
        btn_back_payment_list = (Button) findViewById(R.id.btn_back_payment_list);
        mViewPager=(ViewPager)findViewById(R.id.container);
        tabLayout=(TabLayout)findViewById(R.id.payment_list_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("결제내역"));
        tabLayout.addTab(tabLayout.newTab().setText("충전내역"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_payment_list: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;

                }
            }
        };
        btn_back_payment_list.setOnClickListener(Listener);

    }
}