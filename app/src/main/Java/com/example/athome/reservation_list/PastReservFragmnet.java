package com.example.athome.reservation_list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.example.athome.R;
import com.example.athome.RestRequestHelper;
import com.example.athome.account.AccountCarList;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.MarkerResult;
import com.example.athome.retrofit.ReservationListResult_data;
import com.example.athome.retrofit.requestDeleteResult;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;

public class PastReservFragmnet extends Fragment {
    private ListView past_reserv_listVeiw = null;
    private ArrayList<ItemPastReservData> data = null;
    private PastReservListAdapter adapter;
    private requestDeleteResult requestRes;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.past_reserv_fragmnet, container, false);
        past_reserv_listVeiw = (ListView) view.findViewById(R.id.past_reserv_listView);
        context = container.getContext();
        final Bundle bundle = getArguments();
        final ArrayList<ReservationListResult_data> past = bundle.getParcelableArrayList("past");
        final String sharedToken = bundle.getString("String sharedToken = ");
        ArrayList<ItemPastReservData> itemList = new ArrayList<>();
        ItemPastReservData temp;
        String StartTime = "";
        String EndTime = "";
        for (int i = 0; i < past.size(); i++) {
            StartTime = past.get(i).getStartTime();
            String a = StartTime.substring(0, 20);
            String b = StartTime.substring(30);
            StartTime = a + b;
            
            EndTime = past.get(i).getEndTime();
            a = EndTime.substring(0, 20);
            b = EndTime.substring(30);
            EndTime = a + b;
            
            SimpleDateFormat sdfToDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
            final SimpleDateFormat sdfDate = new SimpleDateFormat("yy-MM-dd");
            final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            try {
                final Date pastReserveStart = sdfToDate.parse(StartTime);
                final Date pastReserveEnd = sdfToDate.parse(EndTime);
                final String pastReserveStartDate = sdfDate.format(pastReserveStart);
                final String pastReserveEndDate = sdfDate.format(pastReserveEnd);
                final String pastReserveStartTime = sdfTime.format(pastReserveStart);
                final String pastReserveEndTime = sdfTime.format(pastReserveEnd);
                final String pastReserveCarNumber = past.get(i).getCarNumber();
                final String pastReserveParkingNumber = past.get(i).getParkingInfo();
                final String pastReserveParkingAddress = past.get(i).getLocation();

                temp = new ItemPastReservData(pastReserveStartDate, pastReserveEndDate, pastReserveStartTime, pastReserveEndTime,
                        pastReserveCarNumber, pastReserveParkingNumber, pastReserveParkingAddress);
                itemList.add(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //서버와 연동해서 값 띄워야함
        data = new ArrayList<>();


        //리스트에 추가
        for (int i = 0; i < itemList.size(); i++) {
            data.add(itemList.get(i));
        }


        //리스트 속의 아이템 연결
        adapter = new PastReservListAdapter(getContext(), R.layout.past_reserv_listview_item, data);
        past_reserv_listVeiw.setAdapter(adapter);

        //해당 지난내역 클릭시 삭제
        past_reserv_listVeiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("지난내역 삭제")
                        .setMessage("해당 내역을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Log.i("jiwon",past.get(position).getId());
                                Log.i("jiwon",past.get(position).getStartTime());
                                Log.i("jiwon",past.get(position).getEndTime());

                                ApiService serviceApi = new RestRequestHelper().getApiService();
                                final Call<requestDeleteResult> res = serviceApi.requestDelete(sharedToken, past.get(position).getId());

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            requestRes = res.execute().body();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(requestRes != null){
                                    if(requestRes.getResult().equals("success")){
                                        Toast.makeText(context, requestRes.getMessage(), Toast.LENGTH_SHORT).show();
                                        data.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                    else{
                                        Toast.makeText(context, requestRes.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }else{

                                }

                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();

                return;
            }
        });


        return view;

    }
}
