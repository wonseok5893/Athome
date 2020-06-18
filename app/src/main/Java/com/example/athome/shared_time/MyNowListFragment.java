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

import java.util.ArrayList;

public class MyNowListFragment extends Fragment {
    private ListView my_now_list_listView = null;
    private ArrayList<ItemMyNowListData> data = null;
    MyNowListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_now_list_fragment, container, false);
        my_now_list_listView = (ListView) view.findViewById(R.id.my_now_list_listView);
        ArrayList<ItemMyNowListData> itemList = new ArrayList<>();

        //서버와 연동해서 값 띄워야함
        data = new ArrayList<>();

        ItemMyNowListData list1 = new ItemMyNowListData("2020-06-19","2020-06-19","14:00","15:00","11가1234","01037944084","대기중");

        //리스트에 추가
        data.add(list1);


        //리스트 속의 아이템 연결
        adapter = new  MyNowListAdapter(getContext(), R.layout.my_now_list_item, data);
        my_now_list_listView.setAdapter(adapter);




        return view;

    }
}