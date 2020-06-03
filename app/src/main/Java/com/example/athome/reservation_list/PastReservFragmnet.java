package com.example.athome.reservation_list;

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
import com.example.athome.account.AccountCarList;

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
        ItemPastReservData list1=new ItemPastReservData("2020-05-04","2020-05-05","22:00","01:01","13하4355","구획 73128-4b","강원도 영월군 중동면");
        ItemPastReservData list2=new ItemPastReservData("2020-08-23","2020-08-23","10:30","12:30","13하4355","구획 3322-4a5","서울특별시 노원구");


        //리스트에 추가
        data.add(list1);
        data.add(list2);


        //리스트 속의 아이템 연결
        adapter= new PastReservListAdapter(getContext(),R.layout.past_reserv_listview_item,data);
        past_reserv_listVeiw.setAdapter(adapter);

        //해당 지난내역 클릭시 삭제
        past_reserv_listVeiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("지난내역 삭제")
                        .setMessage("해당 내역을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                data.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("취소",null)
                        .show();

                return;
            }
        });


        return view;

    }
}
