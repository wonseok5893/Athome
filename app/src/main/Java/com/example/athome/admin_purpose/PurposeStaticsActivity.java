package com.example.athome.admin_purpose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.StatisticsResult;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PurposeStaticsActivity extends AppCompatActivity {
    private List<Integer> data = null;
    private BarChart chart;
    private PieChart pieChart;
    private Button backBtn;
    private StatisticsResult statisticsResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose_statics);

        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");
        ApiService apiService = new RestRequestHelper().getApiService();
        final Call<StatisticsResult> res = apiService.adminGetStatistics(sharedToken,"wonseok");

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
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = statisticsResult.getData();
        initView();
        setBarChart(chart);
        setPieChart(pieChart);


    }

    public void initView(){

        chart = (BarChart)findViewById(R.id.tab1_chart_2);
        pieChart = (PieChart)findViewById(R.id.piechart);
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

        chart.addBar(new BarModel("외식", data.get(0), 0xFF56B7F1));
        chart.addBar(new BarModel("쇼핑", data.get(1), 0xFF56B7F1));
        chart.addBar(new BarModel("비즈니스", data.get(2), 0xFF56B7F1));
        chart.addBar(new BarModel("친구·친지방문", data.get(3), 0xFF56B7F1));
        chart.addBar(new BarModel("의료", data.get(4), 0xFF56B7F1));
        chart.addBar(new BarModel("여행·휴가", data.get(5), 0xFF56B7F1));
        chart.addBar(new BarModel("기타", data.get(6), 0xFF56B7F1));

        chart.startAnimation();

    }

    //원형차트설정
    private void setPieChart(PieChart pieChart){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(data.get(0),"외식"));
        yValues.add(new PieEntry(data.get(1),"쇼핑"));
        yValues.add(new PieEntry(data.get(2),"비즈니스"));
        yValues.add(new PieEntry(data.get(3),"친구·친지방문"));
        yValues.add(new PieEntry(data.get(4),"의료"));
        yValues.add(new PieEntry(data.get(5),"여행·휴가"));
        yValues.add(new PieEntry(data.get(6),"기타"));


        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);


    }

}