package com.example.athome.payment_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.reservation_list.ItemNowReservData;

import java.util.ArrayList;

public class PaymentListAdapter  extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemPayListData> data = null;
    private int layout;

    public  PaymentListAdapter(Context context, int layout, ArrayList<ItemPayListData> data)
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
            convertView = inflater.inflate(R.layout.pay_listview_item, parent, false);
        }

        ItemPayListData itemPayListData=data.get(position);

        TextView payment_date = (TextView) convertView.findViewById(R.id.payment_date);
        TextView payment_amount = (TextView) convertView.findViewById(R.id.payment_amount);
        TextView payment_history = (TextView) convertView.findViewById(R.id.payment_history);


        payment_date.setText(itemPayListData.getPaymentDate());
        payment_amount.setText(itemPayListData.getPaymentAmount());
        payment_history.setText(itemPayListData.getPaymentHistory());


        return convertView;
    }

}
