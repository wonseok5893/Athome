package com.example.athome.parking_details;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.athome.R;
import com.example.athome.reservation_list.ItemPastReservData;
import com.example.athome.reservation_list.PastReservListAdapter;

import java.util.ArrayList;

public class BasicInfoFragment  extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_basic_info_fragment, container, false);



        return view;

    }
}
