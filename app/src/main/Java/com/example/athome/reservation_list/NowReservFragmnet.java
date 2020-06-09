package com.example.athome.reservation_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.athome.R;
import com.example.athome.notice.NoticeClicked;
import com.example.athome.retrofit.ReservationListResult;
import com.example.athome.retrofit.ReservationListResult_data;
import com.naver.maps.map.LocationSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class NowReservFragmnet extends Fragment {
    private ListView now_reserv_listView = null;
    private ArrayList<ItemNowReservData> data = null;
    NowReservListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.now_reserv_fragment, container, false);

        now_reserv_listView = view.findViewById(R.id.now_reserv_listView);

        //서버와 연동해서 값 띄워야함
        data = new ArrayList<>();
        //아이템들

        Bundle bundle = getArguments();
        final ReservationListResult_data current = (ReservationListResult_data) bundle.getSerializable("current");
        if (current != null) {
            String StartTime = "";
            StartTime = current.getStartTime();
            StartTime = StartTime.replace(" GMT+00:00 ", " ");
            String EndTime = "";
            EndTime = current.getEndTime();
            EndTime = EndTime.replace(" GMT+00:00 ", " ");
            SimpleDateFormat sdfToDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
            final SimpleDateFormat sdfDate = new SimpleDateFormat("yy-MM-dd");
            final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            try {
                final Date nowReserveStart = sdfToDate.parse(StartTime);
                final Date nowReserveEnd = sdfToDate.parse(EndTime);
                final String nowReserveStartDate = sdfDate.format(nowReserveStart);
                final String nowReserveEndDate = sdfDate.format(nowReserveEnd);
                final String nowReserveStartTime = sdfTime.format(nowReserveStart);
                final String nowReserveEndTime = sdfTime.format(nowReserveEnd);
                final String nowReserveCarNumber = current.getCarNumber();
                final String nowReserveParkingNumber = current.getParkingInfo();
                final String nowReserveState;
                Date now = new Date();
                SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
                String nowTime = sdfNow.format(now);
                now = sdfNow.parse(nowTime);
                Date Start = sdfNow.parse(nowReserveStartDate);

                if (now.getTime() < Start.getTime()) {
                    nowReserveState = "대기 중";
                } else {
                    nowReserveState = "주차 중";
                }
                ItemNowReservData list1 = new ItemNowReservData(nowReserveStartDate, nowReserveEndDate, nowReserveStartTime, nowReserveEndTime
                        , nowReserveCarNumber, nowReserveParkingNumber, nowReserveState);

                //리스트에 추가
                data.add(list1);

                //리스트 속의 아이템 연결
                adapter = new NowReservListAdapter(getContext(), R.layout.now_reserv_listview_item, data);
                now_reserv_listView.setAdapter(adapter);

                //아이템 클릭시 작동
                now_reserv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), NowReservTicket.class);
                        intent.putExtra("nowReserveStartDate", nowReserveStartDate);
                        intent.putExtra("nowReserveEndDate", nowReserveEndDate);
                        intent.putExtra("nowReserveStartTime", nowReserveStartTime);
                        intent.putExtra("nowReserveEndTime", nowReserveEndTime);
                        intent.putExtra("nowReserveCarNumber", nowReserveCarNumber);
                        intent.putExtra("nowReserveParkingNumber", nowReserveParkingNumber);
                        intent.putExtra("nowReserveState", nowReserveState);
                        startActivity(intent);
                    }
                });
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return view;

    }


}

