package com.example.athome.reservation_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.reservation_list.ItemPastReservData;

import java.util.ArrayList;

public class PastReservListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemPastReservData> data = null;
    private int layout;

    public PastReservListAdapter(Context context, int layout, ArrayList<ItemPastReservData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout=layout;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public String getItem(int position)
    {
        return null;
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
            convertView = inflater.inflate(R.layout.past_reserv_listview_item, parent, false);
        }

        ItemPastReservData itemPastReservData=data.get(position);

        TextView textDate = (TextView) convertView.findViewById(R.id.past_reserv_date);
        TextView textPlace = (TextView) convertView.findViewById(R.id.past_reserv_place_value);
        TextView textTime = (TextView) convertView.findViewById(R.id.past_reserv_time_value);
        TextView textPrice = (TextView) convertView.findViewById(R.id.past_reserv_price_value);

        textDate.setText(itemPastReservData.getPastReservDate());
        textPlace.setText(itemPastReservData.getPastReservPlace());
        textTime.setText(itemPastReservData.getPastReservTime());
        textPrice.setText(itemPastReservData.getPastReservPrice());

        return convertView;
    }

}
