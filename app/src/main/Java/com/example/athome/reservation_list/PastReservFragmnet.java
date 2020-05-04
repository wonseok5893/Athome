package com.example.athome.reservation_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.athome.R;

import java.util.ArrayList;

public class PastReservFragmnet  extends Fragment {
    private ListView past_reserv_listVeiw = null;
    private ArrayList<ItemPastReservData> data=null;
    PastReservListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.past_reserv_fragmnet, container, false);
        past_reserv_listVeiw=(ListView)view.findViewById(R.id.past_reserv_listView);


        //서버와 연동해서 값 띄워야함
        data=new ArrayList<>();
        //아이템들
        ItemPastReservData list1=new ItemPastReservData("2020-05-04","수원시 장안구 연무동 56-39 서진주택","2시간","1200원");
        ItemPastReservData list2=new ItemPastReservData("2020-04-16","경기대학교 본관","1시간","700원");
        ItemPastReservData list3=new ItemPastReservData("2020-03-25","서울특별시 강남대로 12-4길","4시간","2000원");
        ItemPastReservData list4=new ItemPastReservData("2020-02-28","수원시 장안구 연무동 56-39 서진주택","1시간","600원");
        ItemPastReservData list5=new ItemPastReservData("2020-02-15","수원시 장안구 연무동 56-39 서진주택","30분","300원");
        ItemPastReservData list6=new ItemPastReservData("2020-02-11","수원시 장안구 연무동 56-39 서진주택","1시간 30분","1900원");

        //리스트에 추가
        data.add(list1);
        data.add(list2);
        data.add(list3);
        data.add(list4);
        data.add(list5);
        data.add(list6);

        //리스트 속의 아이템 연결
        adapter= new PastReservListAdapter(getContext(),R.layout.past_reserv_listview_item,data);
        past_reserv_listVeiw.setAdapter(adapter);


        return view;

    }
}
