package com.example.athome.admin_notice;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.notice.ItemNoticeData;

import java.util.ArrayList;
import java.util.List;

public class  AdminListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemAdminNoticeData> data = null;
    private int layout;

    //생성자
    public AdminListAdapter(Context context, int layout, List<ItemAdminNoticeData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = (ArrayList<ItemAdminNoticeData>) data;
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
        return data.get(position).getNoticeTitle();
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
            convertView = inflater.inflate(R.layout.admin_notice_listview_item, parent, false);
        }

        ItemAdminNoticeData itemAdminNoticeData = data.get(position);

        TextView textTitle = (TextView) convertView.findViewById(R.id.admin_notice_title);
        TextView textDate = (TextView) convertView.findViewById(R.id.admin_notice_date);


        textTitle.setText(itemAdminNoticeData.getNoticeTitle());
        textDate.setText(itemAdminNoticeData.getNoticeDate());



        return convertView;
    }


}

