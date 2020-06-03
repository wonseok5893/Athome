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

import java.util.ArrayList;
import java.util.HashMap;

public class NowReservFragmnet extends Fragment {
    private ListView now_reserv_listView = null;
    private ArrayList<ItemNowReservData> data=null;
    NowReservListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.now_reserv_fragment, container, false);

        now_reserv_listView=view.findViewById(R.id.now_reserv_listView);

        //서버와 연동해서 값 띄워야함
        data=new ArrayList<>();
        //아이템들
        ItemNowReservData list1=new ItemNowReservData("20-05-29","20-05-29","13:30","14:30","11가1234","구획04-15-a03","주차중");

        //리스트에 추가
        data.add(list1);

        //리스트 속의 아이템 연결
        adapter= new NowReservListAdapter(getContext(),R.layout.now_reserv_listview_item,data);
        now_reserv_listView.setAdapter(adapter);

        //아이템 클릭시 작동
        now_reserv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NowReservTicket.class);
                startActivity(intent);
            }
        });


        return view;

    }



}

