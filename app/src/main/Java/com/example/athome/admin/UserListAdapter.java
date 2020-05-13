package com.example.athome.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.athome.R;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<AllUserData> data = null;
    private int layout;

    //생성자
    public UserListAdapter(Context context, int layout, ArrayList<AllUserData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
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
        return data.get(position).getUserId();
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
            convertView = inflater.inflate(R.layout.admin_users_data, parent, false);
        }

        AllUserData usersData=data.get(position);
        TextView userId = convertView.findViewById(R.id.allUserId);
        TextView userName = (TextView) convertView.findViewById(R.id.allUserName);
        TextView userPhone = (TextView) convertView.findViewById(R.id.allUserPhone);


        userId.setText(usersData.getUserId());
        userName.setText(usersData.getUserName());
        userPhone.setText(usersData.getUserPhone());


        return convertView;
    }

}
