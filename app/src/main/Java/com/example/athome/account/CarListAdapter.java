package com.example.athome.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter  extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemAccountCarData> data = null;
    private int layout;


    //생성자
    public CarListAdapter(Context context, int layout, ArrayList<ItemAccountCarData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = (ArrayList<ItemAccountCarData>) data;
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
        return data.get(position).getCarNumber();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.account_car_item, parent, false);
        }

        ItemAccountCarData itemAccountCarData=data.get(position);

        TextView textCarNumber = (TextView) convertView.findViewById(R.id.car_number_text);
        textCarNumber.setText(itemAccountCarData.getCarNumber());

        return convertView;
    }
}
