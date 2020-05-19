package com.example.athome.point_charge;

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

public class PaymentCardListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemPaymentCardData> data = null;
    private int layout;

    //생성자
    public PaymentCardListAdapter(Context context, int layout, List<ItemPaymentCardData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = (ArrayList<ItemPaymentCardData>) data;
        this.layout=layout;
    }

    public String imhere() {
        return "저 여기있어요";
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
            convertView = inflater.inflate(R.layout.point_payment_dialog_item, parent, false);
        }

        ItemPaymentCardData ItemPaymentCardData=data.get(position);

        TextView textCardCompany = (TextView) convertView.findViewById(R.id.point_payment_card_company_value);
        TextView textCardNumberValue1  = (TextView) convertView.findViewById(R.id.point_payment_card_number_value1);
        TextView textCardNumberValue2  = (TextView) convertView.findViewById(R.id.point_payment_card_number_value2);

        textCardCompany.setText(ItemPaymentCardData.getCardCompany());
        textCardNumberValue1.setText(String.valueOf(ItemPaymentCardData.getCardNumberValue1()));
        textCardNumberValue2.setText(String.valueOf(ItemPaymentCardData.getCardNumberValue2()));

        return convertView;
    }

}

