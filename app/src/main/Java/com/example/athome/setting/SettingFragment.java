package com.example.athome.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;


import com.example.athome.R;

import java.util.prefs.PreferencesFactory;

public class SettingFragment extends PreferenceFragment {
    SharedPreferences pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*별도의 화면 레이아웃파일을 사용하지 않고
        설정 xml문서를 통해 화면이 자동생성
         */
        addPreferencesFromResource(R.xml.setting);
        pref= PreferenceManager.getDefaultSharedPreferences(getActivity());


    }


}
