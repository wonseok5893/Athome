package com.example.athome.admin_enroll;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.notice.ItemNoticeData;


import java.util.ArrayList;

public class AdminEnrollAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<AdminEnrollData> data = null;
    private int layout;

    public AdminEnrollAdapter(Context context, int layout, ArrayList<AdminEnrollData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout=layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getLocation();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.admin_enroll_listview_item,parent,false);
        }
        AdminEnrollData adminEnrollData=data.get(position);
        TextView textEnrollTime = convertView.findViewById(R.id.enlist_num_value);
        TextView textId = (TextView)convertView.findViewById(R.id.enlist_id_value);
        TextView textPhnum = (TextView)convertView.findViewById(R.id.enlist_phone_value);
        TextView textLoc = (TextView)convertView.findViewById(R.id.enlist_loc_value);


        textEnrollTime.setText(adminEnrollData.getEnrollTime().toString());
        textId.setText(adminEnrollData.getOwner().getUserId());
        textPhnum.setText(adminEnrollData.getOwner().getUserPhone());
        textLoc.setText(adminEnrollData.getLocation());


        return convertView;
    }

}
