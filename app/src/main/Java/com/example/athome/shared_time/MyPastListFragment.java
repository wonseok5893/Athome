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

import java.util.ArrayList;

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

        ItemMyPastListData list1 = new ItemMyPastListData("2020-06-19","2020-06-19","14:00","15:00","11가1234","01037944084");

        //리스트에 추가
        data.add(list1);


        //리스트 속의 아이템 연결
        adapter = new  MyPastListAdapter(getContext(), R.layout.my_past_list_item, data);
        my_past_list_listView.setAdapter(adapter);
        //해당 내역 클릭시 삭제
        my_past_list_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                        .setNegativeButton("취소", null)
                        .show();

                return;
            }
        });

        return view;

    }
}