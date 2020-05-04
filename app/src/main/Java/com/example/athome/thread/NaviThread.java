package com.example.athome.thread;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.NaviResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaviThread extends Thread {
    final static String TAG = "NaviThread";
    Context mContext;
    NaviResult naviResult;
    Handler handler;

    String start;
    String goal;

    public NaviThread(Handler handler, Context mContext, String s_lat, String s_lon, String g_lat, String g_lon) {
        this.mContext = mContext;
        this.start = s_lat.concat(",").concat(s_lon);
        this.goal = g_lat.concat(",").concat(g_lon);
        // lat 위도 124-132
        // lon 경도 33-43
        this.handler = handler;
    }

    @Override
    public void run(){
        super.run();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_NAVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<NaviResult> call = apiService.getNavigate(start,goal); //http 통신 과정 (윤지원 05-04)
        call.enqueue(new Callback<NaviResult>() {
            @Override
            public void onResponse(Call<NaviResult> call, Response<NaviResult> response) {
                if(response.isSuccessful()) {
                    naviResult = response.body();
                    Log.d(TAG,"response.raw"+response.raw()); //http raw 데이터 로그
                    if(naviResult.getCode().equals("0")); // 0 -> 길찾기 완료 코드 (윤지원 05-04)
                    {
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("NaviResult",naviResult); //bundle에 응답값 객체 넘김 (윤지원 05-04)
                        msg.setData(bundle);
                        msg.what = 0;
                        handler.sendMessage(msg); //Mainactivity 핸들러 메세지큐로 넘김
                    }
            }
                else{
                    Log.e(TAG,"요청 실패 :"+naviResult.getCode());
                    Log.e(TAG,"메시지 :"+naviResult.getMessage());
                }
            }

            @Override
            public void onFailure(Call<NaviResult> call, Throwable t) {
                Log.e(TAG,"네비정보 불러오기 실패 :" + t.getMessage() );
                Log.e(TAG,"요청 메시지 :"+call.request());
            }
        });
    }
}
