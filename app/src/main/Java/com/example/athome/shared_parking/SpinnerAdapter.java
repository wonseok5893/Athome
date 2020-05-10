package com.example.athome.shared_parking;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            // TODO Auto-generated constructor stub
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v=super.getView(position,convertView,parent);
        if(position == getCount()){
            ((TextView) v.findViewById(android.R.id.text1)).setText("");
            ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
        }

        return v;
    }
             @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = super.getCount();
        return count>0 ? count-1 : count ;


        }


}
