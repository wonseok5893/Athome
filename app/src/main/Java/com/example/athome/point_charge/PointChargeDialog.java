package com.example.athome.point_charge;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.athome.R;

public class PointChargeDialog extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle saveInsanceState) {
        super.onCreate(saveInsanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.point_payment_dialog);
    }

    @Override
    public void onClick(View v) {

    }
}
