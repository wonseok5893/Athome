package com.example.athome.reservation_list;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.athome.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NowReservFragmnet extends Fragment {
    private ExpandableListView now_reserv_listView;
    private ExpandAdapter adapter;
    private ArrayList<NowReservParentItem> data = new ArrayList<NowReservParentItem>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.now_reserv_fragment, container, false);

        now_reserv_listView = (ExpandableListView) view.findViewById(R.id.now_reserv_listView);

        NowReservParentItem group1= new NowReservParentItem("이용중","서진주택 #12345", "2020/05/07");
        group1.child.add(new NowReservChildItem("서진주택 #701537","강원도 영월군 중동면 영월로 3815-5 연상초등학교","2020/05/06","2020/05/06",
                "2020/05/07","11가1111","대기중"));
        data.add(group1);



        adapter = new ExpandAdapter(getContext(),data);
        now_reserv_listView.setAdapter(adapter);
        now_reserv_listView.setGroupIndicator(null); //그룹 왼쪽에 기본제공되는 화살표 아이콘 삭제
        return view;
    }



    }

