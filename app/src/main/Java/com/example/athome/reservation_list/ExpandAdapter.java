package com.example.athome.reservation_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.athome.R;

import java.util.ArrayList;

public class ExpandAdapter extends BaseExpandableListAdapter{
    private Context context;
    public ArrayList<NowReservParentItem> parentItems; //부모 리스트를 담을 배열
    private LayoutInflater inflater = null;

    public ExpandAdapter(Context context,  ArrayList<NowReservParentItem> parentItems) {
        this.parentItems = parentItems;
        this.context = context;
        this.inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            Context context=parent.getContext();

            //convertView가 비어있을 경우 xml파일을 inflate 해줌
            //캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.now_reserv_group_row, parent, false);
                viewHolder=new ViewHolder();
                viewHolder.groupState=(TextView) convertView.findViewById(R.id.reserv_state);
                viewHolder.groupParking= (TextView) convertView.findViewById(R.id.now_reserv_parking_group);
                viewHolder.groupDate= (TextView) convertView.findViewById(R.id.now_reserv_date_group);

                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            if(parentItems!=null) {

                viewHolder.groupState.setText(parentItems.get(groupPosition).getGroupState());
                viewHolder.groupParking.setText(parentItems.get(groupPosition).getGroupParking());
                viewHolder.groupDate.setText(parentItems.get(groupPosition).getGroupDate());
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            Context context = parent.getContext();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.now_reserv_child_row, parent, false);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                viewHolder=new ViewHolder();

                viewHolder.childParking = (TextView) convertView.findViewById(R.id.now_reserv_parking_value);
                viewHolder.childPlace = (TextView) convertView.findViewById(R.id.now_reserv_place_value);
                viewHolder.childPaydate = (TextView) convertView.findViewById(R.id.now_reserv_paydate_value);
                viewHolder.childDate = (TextView) convertView.findViewById(R.id.now_reserv_date_value);
                viewHolder.childTime = (TextView) convertView.findViewById(R.id.now_reserv_time_value);
                viewHolder.childCar = (TextView) convertView.findViewById(R.id.now_reserv_car_value);
                viewHolder.childState = (TextView) convertView.findViewById(R.id.now_reserv_state_value);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            if(parentItems!=null) {

                viewHolder.childParking.setText(parentItems.get(groupPosition).child.get(childPosition).getChildParking());
                viewHolder.childPlace.setText(parentItems.get(groupPosition).child.get(childPosition).getChildPlace());
                viewHolder.childPaydate.setText(parentItems.get(groupPosition).child.get(childPosition).getChildPaydate());
                viewHolder.childDate.setText(parentItems.get(groupPosition).child.get(childPosition).getChildDate());
                viewHolder.childTime.setText(parentItems.get(groupPosition).child.get(childPosition).getChildTime());
                viewHolder.childCar.setText(parentItems.get(groupPosition).child.get(childPosition).getChildCar());
                viewHolder.childState.setText(parentItems.get(groupPosition).child.get(childPosition).getChildState());
            }
            return convertView;
        }




    //각 리스트의 크기 반환
    @Override
    public int getGroupCount() {
        if(parentItems==null)
            return 0;
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(parentItems==null)
            return 0;
        return parentItems.get(groupPosition).child.size();
    }

    //리스트의 아이템 반환
    @Override
    public Object getGroup(int groupPosition) {
        return parentItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parentItems.get(groupPosition).child.get(childPosition);
    }

    //리스트 아이템의 id 반환
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //동일한 id가 항상 동일한 개체를 참조하는지 여부를 반환
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder{
        TextView groupState;
        TextView groupParking;
        TextView groupDate;

        TextView childParking;
        TextView childPlace;
        TextView childPaydate;
        TextView childDate;
        TextView childTime;
        TextView childCar;
        TextView childState;
    }






}
