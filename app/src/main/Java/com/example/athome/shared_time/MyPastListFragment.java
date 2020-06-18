package com.example.athome.shared_time;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.athome.R;
import com.example.athome.payment_list.ItemPayListData;
import com.example.athome.payment_list.PaymentListAdapter;
import com.example.athome.reservation_list.ItemPastReservData;
import com.example.athome.retrofit.myParkingResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyPastListFragment extends Fragment {
    private ListView my_past_list_listView = null;
    private ArrayList<ItemMyPastListData> data = null;
    MyPastListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_past_list_fragment, container, false);
        my_past_list_listView = (ListView) view.findViewById(R.id.my_past_list_listView);
        ArrayList<ItemMyPastListData> itemList = new ArrayList<>();

        //서버와 연동해서 값 띄워야함
        data = new ArrayList<>();


        Bundle bundle = getArguments();
        ArrayList<myParkingResult.Data> tmp = bundle.getParcelableArrayList("past");
        ItemMyPastListData list1 = null;
        String StartTime;
        String EndTime;

        if(tmp != null){
            for(myParkingResult.Data r : tmp){
                StartTime = r.getStartTime();
                String a = StartTime.substring(0,20);
                String b = StartTime.substring(30);
                StartTime = a+b;

                EndTime = r.getEndTime();
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
                    final String nowReserveCarNumber = r.getCarNumber();

                    list1 = new ItemMyPastListData(nowReserveStartDate, nowReserveEndDate, nowReserveStartTime, nowReserveEndTime
                            , nowReserveCarNumber, r.getPhoneNumber());

                /*
                * String pastReservStartDate,
                              String pastReservEndDate,
                              String pastReservStartTime,
                              String pastReservEndTime,
                              String pastReservCarNumber,
                              String pastReservPhoneNumber*/
                    itemList.add(list1);
                    data.add(list1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }


        //리스트 속의 아이템 연결
        adapter = new  MyPastListAdapter(getContext(), R.layout.my_past_list_item, data);
        my_past_list_listView.setAdapter(adapter);


        return view;

    }
}