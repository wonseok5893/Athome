package com.example.athome.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.account.ItemAccountCardData;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemNotificationData> data = null;
    private int layout;

    //생성자
    public NotificationAdapter(Context context, int layout, List<ItemNotificationData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = (ArrayList<ItemNotificationData>) data;
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
        return data.get(position).getNotificationTitle();
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
            convertView = inflater.inflate(R.layout.notification_listview_item, parent, false);
        }

        ItemNotificationData itemNotificationData=data.get(position);

        TextView textNotificationTitle = (TextView) convertView.findViewById(R.id.notification_title);
        TextView textNotificationContext = (TextView) convertView.findViewById(R.id.notification_context);
        TextView textNotificationDate = (TextView) convertView.findViewById(R.id.notification_date);
        TextView textNotificationTime = (TextView) convertView.findViewById(R.id.notification_time);


        textNotificationTitle.setText(itemNotificationData.getNotificationTitle());
        textNotificationContext.setText(itemNotificationData.getNotificationContext());
        textNotificationDate.setText(itemNotificationData.getNotificationDate());
        textNotificationTime.setText(itemNotificationData.getNotificationTime());


        return convertView;
    }

}
