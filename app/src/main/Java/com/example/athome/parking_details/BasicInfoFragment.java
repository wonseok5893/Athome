package com.example.athome.parking_details;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.athome.R;
import com.example.athome.reservation_list.ItemPastReservData;
import com.example.athome.reservation_list.PastReservListAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BasicInfoFragment  extends Fragment {

    String locationName;
    String parkingInfo;
    InputStream is;

    TextView details_parking_address, details_parking_number;
    ImageView parking_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_basic_info_fragment, container, false);

        String uri = getArguments().getString("uri");


        parking_image = view.findViewById(R.id.parking_image);
        String imageUrl = "http://222.251.129.150/"+uri.replace("\\","/");
        Log.i("jiwon",imageUrl);
        Glide.with(this).load(imageUrl).into(parking_image);


        if(getArguments() != null) {
            locationName = getArguments().getString("locationName");
            parkingInfo = getArguments().getString("parkingInfo");
        }

        details_parking_address = (TextView) view.findViewById(R.id.details_parking_address);
        details_parking_number = (TextView) view.findViewById(R.id.details_parking_number);

        details_parking_address.setText(locationName);
        details_parking_number.setText(parkingInfo);

        return view;

    }

}
