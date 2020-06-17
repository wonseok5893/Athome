package com.example.athome.parking_details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.athome.R;

import java.util.Arrays;

public class TimeInfoFragment extends Fragment {

    private int[] timeArray;
    private String startTime;
    private String endTime;
    TextView[] day_time;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_time_info_fragment, container, false);

        day_time = new TextView[7];
        if(getArguments() != null) {
            timeArray = getArguments().getIntArray("timeArray");
            startTime = getArguments().getString("startTime");
            endTime = getArguments().getString("endTime");


            day_time[0] = (TextView) view.findViewById(R.id.sun_time);
            day_time[1] = (TextView) view.findViewById(R.id.mon_time);
            day_time[2] = (TextView) view.findViewById(R.id.tue_time);
            day_time[3] = (TextView) view.findViewById(R.id.wed_time);
            day_time[4] = (TextView) view.findViewById(R.id.thu_time);
            day_time[5] = (TextView) view.findViewById(R.id.fri_time);
            day_time[6] = (TextView) view.findViewById(R.id.sat_time);

            for(int i=0;i<7;i++) {
                if(timeArray[i]!=0) {
                    day_time[i].setText(startTime + " ~ " + endTime);
                }
            }
        }

        Log.d("제발이것좀", Arrays.toString(timeArray)+startTime+endTime);


        return view;

    }
}
