package com.example.athome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.StatisticsResult;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;


import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class PurposeStaticsActivity extends AppCompatActivity {
    private List<Integer> data = null;
    private BarChart chart;
    private Button backBtn;
    private StatisticsResult statisticsResult;
    private Spinner spinner_area;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose_statics);

        initView();
        data = api(titles[1]);
        setBarChart();
    }

    private List<Integer> api(String location) {
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");

        ApiService apiService = new RestRequestHelper().getApiService();
        final Call<StatisticsResult> res = apiService.adminGetStatistics(sharedToken, location);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    statisticsResult = res.execute().body();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (statisticsResult != null) {
            List<Integer> data = statisticsResult.getData();
            return data;
        } else {
            return null;
        }

    }


    public void initView() {

        chart = (BarChart) findViewById(R.id.tab1_chart_2);
        backBtn = findViewById(R.id.static_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
            }
        });
        //구 선택하는 스피너

        Resources res = getResources();
        titles = res.getStringArray(R.array.select_gu);

        spinner_area = findViewById(R.id.spinner_area);
        ArrayAdapter<CharSequence> adapterLocal = ArrayAdapter.createFromResource(
                this, R.array.select_gu, android.R.layout.simple_spinner_dropdown_item);
        spinner_area.setAdapter(adapterLocal);
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    data = api(titles[position]);
                    setBarChart();
                } else {
                    chart.clearChart();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // 막대 차트 설정
    private void setBarChart() {
        chart.clearChart();
        if (data != null) {

            chart.addBar(new BarModel("외식", data.get(0), 0xFF56B7F1));
            chart.addBar(new BarModel("쇼핑", data.get(1), 0xFF56B7F1));
            chart.addBar(new BarModel("비즈니스", data.get(2), 0xFF56B7F1));
            chart.addBar(new BarModel("친구·친지방문", data.get(3), 0xFF56B7F1));
            chart.addBar(new BarModel("의료", data.get(4), 0xFF56B7F1));
            chart.addBar(new BarModel("여행·휴가", data.get(5), 0xFF56B7F1));
            chart.addBar(new BarModel("기타", data.get(6), 0xFF56B7F1));

            chart.startAnimation();
        }

    }
}