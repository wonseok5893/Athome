package com.example.athome.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.notification.ItemNotificationData;

import java.util.ArrayList;
import java.util.List;

public class ItemNoticeListAdaptor extends BaseAdapter{

        LayoutInflater inflater = null;
        private List<ItemNoticeData> data = null;
        private int layout;

        //생성자
        public ItemNoticeListAdaptor(Context context, int layout, List<ItemNoticeData> data)
        {
            this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data = (List<ItemNoticeData>) data;
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
            return data.get(position).getTitle();
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
                convertView = inflater.inflate(R.layout.notice_listview_item, parent, false);
            }

            ItemNoticeData itemNoticeData=data.get(position);

            TextView noticeTitle = (TextView) convertView.findViewById(R.id.notice_title);
            TextView noticeDate = (TextView) convertView.findViewById(R.id.notice_date);

            String noticeEnrollTime = itemNoticeData.getEnrollTime().substring(0,13);


            noticeTitle.setText(itemNoticeData.getTitle());
            noticeDate.setText(noticeEnrollTime);



            return convertView;
        }

    }


