package com.example.athome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;

import java.util.List;

public class PurposeStaticsActivity<itemModel> extends AppCompatActivity {

    private BarChart chart;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose_statics);
        initView();
        setBarChart(chart);


    }

    public void initView(){

        chart = (BarChart)findViewById(R.id.tab1_chart_2);
        backBtn = findViewById(R.id.static_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
            }
        });
    }

    // 막대 차트 설정
    private void setBarChart(BarChart chart) {

        chart.clearChart();

        chart.addBar(new BarModel("외식", 20f, 0xFF56B7F1));
        chart.addBar(new BarModel("쇼핑", 15f, 0xFF56B7F1));
        chart.addBar(new BarModel("출장·비즈니스", 13f, 0xFF56B7F1));
        chart.addBar(new BarModel("의료·건강", 5f, 0xFF56B7F1));
        chart.addBar(new BarModel("여행·휴가", 1f, 0xFF56B7F1));
        chart.addBar(new BarModel("기타", 6f, 0xFF56B7F1));

        chart.startAnimation();

    }

}
