package com.example.athome.admin_purpose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.payment_list.PayListPagerAdapter;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.StatisticsResult;
import com.google.android.material.tabs.TabLayout;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class PurposeStaticsActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose_statics);
        initView();

        final ViewPager viewPager=(ViewPager)findViewById(R.id.purpose_list_viewpager);
        final PurposePagerAdapter adapter = new  PurposePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
        public void initView () {
            mViewPager=(ViewPager)findViewById(R.id.container);
            tabLayout=(TabLayout)findViewById(R.id.purpose_tablayout);
            tabLayout.addTab(tabLayout.newTab().setText("막대차트"));
            tabLayout.addTab(tabLayout.newTab().setText("원형차트"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            backBtn = findViewById(R.id.static_backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
                }
            });
        }

    }

