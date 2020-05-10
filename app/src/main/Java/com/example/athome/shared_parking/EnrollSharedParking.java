package com.example.athome.shared_parking;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;

import java.util.ArrayList;


public class EnrollSharedParking extends AppCompatActivity {
    private Spinner spinner_location;
    private SpinnerAdapter adapter;

    public EnrollSharedParking() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedparking_assigner_lookup);

        final ArrayList<String> items = new ArrayList<>();
        items.add("경기도 수원시");
        items.add("경기도 용인시");
        items.add("위치선택");
        spinner_location = (Spinner) findViewById(R.id.assigner_parking_location_value);

        adapter = new SpinnerAdapter(EnrollSharedParking.this, android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(items);
        spinner_location.setAdapter(adapter);
        spinner_location.setSelection(adapter.getCount());
        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
