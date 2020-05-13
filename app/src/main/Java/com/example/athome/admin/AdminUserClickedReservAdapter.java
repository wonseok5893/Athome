package com.example.athome.admin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;

import java.util.ArrayList;

public class AdminUserClickedReservAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<AdminUserClickedReservData> data = null;
    private int layout;

    //생성자
    public AdminUserClickedReservAdapter(Context context, int layout, ArrayList<AdminUserClickedReservData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout=layout;
    }

    //화면 갱신되기전 호출
    //처음 생성될때도 getCount()가 호출되어 아이템이 몇개 그려질지 결정
    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public String getItem(int position)
    {
        return data.get(position).getCarnum();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    //리턴할때 각 아이템레이아웃을 넘겨주면 화면에 표시
    //포지션으로 현재 몇번째 아이템이 표시해야되는지 알 수 있음
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.admin_user_clicked_share_item, parent, false);
        }

        AdminUserClickedReservData shareData=data.get(position);
        TextView carnum = convertView.findViewById(R.id.clicked_share_id);
        TextView loc = (TextView) convertView.findViewById(R.id.clicked_share_place);
        TextView resDate = (TextView) convertView.findViewById(R.id.clicked_share_date);
        TextView resStart = (TextView) convertView.findViewById(R.id.clicked_share_start);
        TextView resEnd = (TextView) convertView.findViewById(R.id.clicked_share_end);
        TextView resState = (TextView) convertView.findViewById(R.id.clicked_share_state);

        carnum.setText(shareData.getCarnum());
        loc.setText(shareData.getLoc());
        resDate.setText(shareData.getResv_date());
        resStart.setText(shareData.getResv_start());
        resEnd.setText(shareData.getResv_end());
        resState.setText(shareData.getState());


        return convertView;
    }

}

