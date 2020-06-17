package com.example.athome.admin_purpose;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.gun0912.tedpermission.TedPermissionBase.getSharedPreferences;

public class PurposePieFragment<itemModel> extends Fragment {
    private List<Integer> data = null;
    private PieChart pieChart;
    private StatisticsResult statisticsResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purpose_pie_fragment, container, false);
        pieChart = (PieChart)view.findViewById(R.id.piechart);

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

        return view;

    }
}