package com.example.athome.reservation_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;

import java.util.ArrayList;

public class NowReservListAdapter  extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemNowReservData> data = null;
    private int layout;

    public NowReservListAdapter(Context context, int layout, ArrayList<ItemNowReservData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout=layout;
    }

    @Override
    public int getCount()
    {
        if(data == null){
            return 0;
        }
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
            convertView = inflater.inflate(R.layout.now_reserv_listview_item, parent, false);
        }

        ItemNowReservData itemNowReservData=data.get(position);

        TextView textStartDate = (TextView) convertView.findViewById(R.id.now_reserv_start_date);
        TextView textEndDate = (TextView) convertView.findViewById(R.id.now_reserv_end_date);
        TextView textStartTime = (TextView) convertView.findViewById(R.id.now_reserv_start_time);
        TextView textEndTime = (TextView) convertView.findViewById(R.id.now_reserv_end_time);
        TextView textCarNumber = (TextView) convertView.findViewById(R.id.now_reserv_car_number);
        TextView textParkingNumber = (TextView) convertView.findViewById(R.id.now_reserv_parking_number);
        TextView textState = (TextView) convertView.findViewById(R.id.now_reserv_state);


        textStartDate.setText(itemNowReservData.getNowReservStartDate());
        textEndDate.setText(itemNowReservData.getNowReservEndDate());
        textStartTime.setText(itemNowReservData.getNowReservStartTime());
        textEndTime.setText(itemNowReservData.getNowReservEndTime());
        textCarNumber.setText(itemNowReservData.getNowReservCarNumber());
        textParkingNumber.setText(itemNowReservData.getNowReservParkingNumber());
        textState.setText(itemNowReservData.getNowReservState());

        return convertView;
    }

}
