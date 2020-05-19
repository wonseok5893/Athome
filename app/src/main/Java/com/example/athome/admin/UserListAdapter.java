package com.example.athome.admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.athome.R;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater = null;
    private ArrayList<AllUserData> data = null;
    private ArrayList<AllUserData> filterData = data;
    Filter userFilter = null;
    private int layout;

    //생성자
    public UserListAdapter(Context context, int layout, ArrayList<AllUserData> data)
    {
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout=layout;
        this.filterData = data;
    }

    //화면 갱신되기전 호출
    //처음 생성될때도 getCount()가 호출되어 아이템이 몇개 그려질지 결정
    @Override
    public int getCount()
    {
        return filterData.size();
    }

    @Override
    public String getItem(int position)
    {
        return filterData.get(position).getUserId();
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

        AllUserData usersData = filterData.get(position);

        TextView userId = convertView.findViewById(R.id.allUserId);
        TextView userName = (TextView) convertView.findViewById(R.id.allUserName);
        TextView userPhone = (TextView) convertView.findViewById(R.id.allUserPhone);


        userId.setText(usersData.getUserId());
        userName.setText(usersData.getUserName());
        userPhone.setText(usersData.getUserPhone());


        return convertView;
    }

    // 검색필터 생성
    @Override
    public Filter getFilter() {
        if(userFilter == null) {
            userFilter = new userListFilter();
            Log.d("junggyu", "Filter 생성완료");
        }
        return userFilter;
    }

    // 그냥 검색 후 출력할 대상이 그냥 Text면 Filter상속해줄 필요 없는 듯
    // 객채를 검색, 비교하기 위해서는 filter클래스를 상속해서 내부에 검색 알고리즘을 구현해 주어야 한다.
    private class userListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;

            if (constraint == null || constraint.length() == 0) {
                results.values = data;
                results.count = data.size();
            } else {
                ArrayList<AllUserData> itemList = new ArrayList<AllUserData>() ;
                int i=0;
                for (AllUserData item : data) {
                    if (item.getUserId().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                            item.getUserName().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item) ;
                    }
                }

                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // update listview by filtered data list.
            filterData = (ArrayList<AllUserData>) results.values ;
            Log.d("junggyu", "검색결과  : " + results.count + "명");
            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }
}