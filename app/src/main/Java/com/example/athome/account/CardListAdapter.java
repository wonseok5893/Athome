package com.example.athome.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.notice.ItemNoticeData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemAccountCardData> data = null;
    private int layout;

    //생성자
    public CardListAdapter(Context context, int layout, List<ItemAccountCardData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = (ArrayList<ItemAccountCardData>) data;
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
        return data.get(position).getCardCompany();
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
            convertView = inflater.inflate(R.layout.account_card_listview_item, parent, false);
        }

        ItemAccountCardData itemAccountCardData=data.get(position);

        TextView textCardCompany = (TextView) convertView.findViewById(R.id.card_company_value);
        TextView textCardNumberValue1  = (TextView) convertView.findViewById(R.id.card_number_value1);
        TextView textCardNumberValue2  = (TextView) convertView.findViewById(R.id.card_number_value2);
        TextView textCardNumberValue3  = (TextView) convertView.findViewById(R.id.card_number_value3);
        TextView textCardNumberValue4  = (TextView) convertView.findViewById(R.id.card_number_value4);

        textCardCompany.setText(itemAccountCardData.getCardCompany());
        textCardNumberValue1.setText(String.valueOf(itemAccountCardData.getCardNumberValue1()));
        textCardNumberValue2.setText(String.valueOf(itemAccountCardData.getCardNumberValue2()));
        textCardNumberValue3.setText(String.valueOf(itemAccountCardData.getCardNumberValue3()));
        textCardNumberValue4.setText(String.valueOf(itemAccountCardData.getCardNumberValue4()));

        return convertView;
    }

}
