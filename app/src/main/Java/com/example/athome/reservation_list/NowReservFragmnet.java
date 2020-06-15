package com.example.athome.reservation_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        final ArrayList<ReservationListResult_data> current = bundle.getParcelableArrayList("current");
        ArrayList<ItemNowReservData> itemList = new ArrayList<>();
        Log.i("jiwon", "now"+current.toString());
        Log.i("jiwon", "now"+current.size());
        ItemNowReservData temp;
        String StartTime = "";
        String EndTime = "";
        if (current != null) {
            for (int i = 0; i < current.size(); i++) {

                StartTime = current.get(i).getStartTime();
                String a = StartTime.substring(0,20);
                String b = StartTime.substring(30);
                StartTime = a+b;

                EndTime = current.get(i).getEndTime();
                a = EndTime.substring(0,20);
                b = EndTime.substring(30);
                EndTime = a+b;

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
                    final String nowReserveCarNumber = current.get(i).getCarNumber();
                    final String nowReserveParkingNumber = current.get(i).getParkingInfo();
                    final String nowReserveState;
                    Date now = new Date();
                    SimpleDateFormat sdfNow = new SimpleDateFormat("MM dd HH:mm");
                    String nowTime = sdfNow.format(now);
                    now = sdfNow.parse(nowTime);

                    String StartTemp = sdfNow.format(nowReserveStart);
                    Date Start = sdfNow.parse(StartTemp);

                    if (now.getTime() < Start.getTime()) {
                        nowReserveState = "대기 중";
                    } else {
                        nowReserveState = "주차 중";
                    }
                    temp = new ItemNowReservData(nowReserveStartDate, nowReserveEndDate, nowReserveStartTime, nowReserveEndTime
                            , nowReserveCarNumber, nowReserveParkingNumber, nowReserveState);
                    itemList.add(temp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //리스트에 추가
            for (int i = 0; i < itemList.size(); i++) {
                data.add(itemList.get(i));
            }
        }

        //리스트 속의 아이템 연결
        adapter = new NowReservListAdapter(getContext(), R.layout.now_reserv_listview_item, data);
        now_reserv_listView.setAdapter(adapter);

        //아이템 클릭시 작동
        now_reserv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NowReservTicket.class);
                intent.putExtra("_id",current.get(position).getId());
                intent.putExtra("nowReserveStartDate", data.get(position).getNowReservStartDate());
                intent.putExtra("nowReserveEndDate", data.get(position).getNowReservEndDate());
                intent.putExtra("nowReserveStartTime", data.get(position).getNowReservStartTime());
                intent.putExtra("nowReserveEndTime", data.get(position).getNowReservEndTime());
                intent.putExtra("nowReserveCarNumber", data.get(position).getNowReservCarNumber());
                intent.putExtra("nowReserveParkingNumber", data.get(position).getNowReservParkingNumber());
                intent.putExtra("nowReserveState", data.get(position).getNowReservState());
                startActivity(intent);
            }
        });

        return view;

    }


}

