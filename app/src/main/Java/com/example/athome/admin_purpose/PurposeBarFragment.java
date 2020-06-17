package com.example.athome.admin_purpose;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.StatisticsResult;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

import static com.gun0912.tedpermission.TedPermissionBase.getSharedPreferences;

public class PurposeBarFragment <itemModel> extends Fragment {
    private List<Integer> data = null;
    private BarChart chart;
    private StatisticsResult statisticsResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purpose_bar_fragment, container, false);

        chart = (BarChart)view.findViewById(R.id.tab1_chart_2);

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



        chart.clearChart();

        chart.addBar(new BarModel("외식", data.get(0), 0xFF56B7F1));
        chart.addBar(new BarModel("쇼핑", data.get(1), 0xFF56B7F1));
        chart.addBar(new BarModel("비즈니스", data.get(2), 0xFF56B7F1));
        chart.addBar(new BarModel("친구·친지방문", data.get(3), 0xFF56B7F1));
        chart.addBar(new BarModel("의료", data.get(4), 0xFF56B7F1));
        chart.addBar(new BarModel("여행·휴가", data.get(5), 0xFF56B7F1));
        chart.addBar(new BarModel("기타", data.get(6), 0xFF56B7F1));

        chart.startAnimation();


        return view;
    }

}
