package com.example.athome.reservation_list;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.athome.R;
import com.example.athome.parking_details.ParkingDetailsActivity;
import com.example.athome.reservation.ReserveActivity;
import com.example.athome.retrofit.LocationInfoList;
import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.main.MainActivity;
import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.nearParkResult;
import com.example.athome.retrofit.requestDeleteResult;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class NowReservTicket extends Activity {
    private Button btn_back_reserv_ticket;
    private Button btn_reserv_ticket_payment_cancel; //결제취소
    private Button btn_reserv_ticket_time_extension; //시간연장
    private LinearLayout more_parking_linear; //주차장정보
    final int[] selectdItem = {0};
    private TextView now_reserv_start_date, now_reserv_start_time, now_reserv_end_date, now_reserv_end_time, parking_number, now_reserv_car_number, now_reserv_state_value;
    private String _id;
    private requestDeleteResult rd;
    private LinearLayout illegal;
    private nearParkResult nearRes;
    private String nearId;
    private String parkingInfo;
    private String locationName;
    private LocationInfoList locationRes;
    private String locationStartTime;
    private String locationEndTime;
    private int[] timeArray = new int[7];
    private Double latitude;
    private Double longitude;
    private String description;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.now_reserv_parking_ticket);

        this.InitializeView();
        this.SetListner();
    }

    public void InitializeView() {
        btn_back_reserv_ticket = (Button) findViewById(R.id.btn_back_reserv_ticket);
        btn_reserv_ticket_payment_cancel = (Button) findViewById(R.id.btn_reserv_ticket_payment_cancel);
        btn_reserv_ticket_time_extension = (Button) findViewById(R.id.btn_reserv_ticket_time_extension);
        illegal = (LinearLayout) findViewById(R.id.illegal);

        Intent intent = getIntent();
        now_reserv_start_date = (TextView) findViewById(R.id.now_reserv_start_date);
        now_reserv_start_time = (TextView) findViewById(R.id.now_reserv_start_time);
        now_reserv_end_date = (TextView) findViewById(R.id.now_reserv_end_date);
        now_reserv_end_time = (TextView) findViewById(R.id.now_reserv_end_time);
        parking_number = (TextView) findViewById(R.id.parking_number);
        now_reserv_car_number = (TextView) findViewById(R.id.now_reserv_car_number);
        now_reserv_state_value = (TextView) findViewById(R.id.now_reserv_state_value);
        _id = intent.getStringExtra("_id");
        Log.i("jiwon", _id);

        now_reserv_start_date.setText(intent.getStringExtra("nowReserveStartDate"));
        now_reserv_start_time.setText(intent.getStringExtra("nowReserveStartTime"));
        now_reserv_end_date.setText(intent.getStringExtra("nowReserveEndDate"));
        now_reserv_end_time.setText(intent.getStringExtra("nowReserveEndTime"));
        now_reserv_car_number.setText(intent.getStringExtra("nowReserveParkingNumber"));
        now_reserv_state_value.setText(intent.getStringExtra("nowReserveState"));

    }

    public void SetListner() {
        View.OnClickListener Listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_back_reserv_ticket: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
                        break;
                    case R.id.btn_reserv_ticket_payment_cancel: //결제취소버튼
                        /*종료시간 몇 분전 설정해놓고 그 시간전에 결제 취소를 누르면 결제 취소가능하고
                          결제취소 시간이 지나면 결제취소 불가*/
                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(NowReservTicket.this);
                        dialog1.setTitle("결제를 취소하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                                        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기

                                        ApiService serviceApi = new RestRequestHelper().getApiService();
                                        final Call<requestDeleteResult> res = serviceApi.requestDelete(sharedToken, _id);

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    rd = res.execute().body();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();

                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if (rd.getResult().equals("success")) { // 삭제 성공시
                                            Toast.makeText(getApplicationContext(), rd.getMessage(), Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(getApplicationContext(), com.example.athome.main.MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);

                                        } else { // 삭제 실패시
                                            Toast.makeText(getApplicationContext(), rd.getMessage(), Toast.LENGTH_LONG).show();
                                            finish();
                                        }

                                    }
                                })
                                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(NowReservTicket.this
                                                , "취소"
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog1.create();
                        dialog1.show();
                        break;

                    case R.id.btn_reserv_ticket_time_extension: //시간연장버튼
                        /*만약 연장시간(ex:1시간)선택했을때 연장시간 안에 다른 예약시간과 겹치면 연장불가띄어주고
                         연장이 가능하다면 연장시간만큼 추가 결제해야함 */

                        //시간연장버튼 눌렀을 때 연장할 수 있는 다이얼로그
                        final String[] items = new String[]{"10분", "30분", "1시간"};
                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(NowReservTicket.this);
                        dialog2.setTitle("시간 연장")
                                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectdItem[0] = which;
                                    }
                                })
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(NowReservTicket.this
                                                , "취소"
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog2.create();
                        dialog2.show();
                        break;
                    case R.id.illegal://해당 주차장정보로 넘어가기
                        AlertDialog.Builder dialog = new AlertDialog.Builder(NowReservTicket.this);
                        dialog.setTitle("부정주차 신고하기")
                                .setMessage("부정주차로 신고하시겠습니까?(결제 자동 취소)")
                                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(NowReservTicket.this, "신고가 접수되었습니다.", Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(NowReservTicket.this);
                                        dialog2.setTitle("최근접 빈 공유주차장 추천")
                                                .setMessage("가까운 위치를 원하십니까?")
                                                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(getApplicationContext(), ParkingDetailsActivity.class);
                                                        ApiService serviceApi = new RestRequestHelper().getApiService();
                                                        final Call<nearParkResult> res = serviceApi.requestNearPark(_id);

                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    nearRes = res.execute().body();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }).start();

                                                        try {
                                                            Thread.sleep(500);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (nearRes != null) {
                                                            if (nearRes.getResult().equals("success")) {
                                                                nearId = nearRes.getId();
                                                                parkingInfo = nearRes.getParkingInfo();
                                                                locationName = nearRes.getLocation();
                                                                latitude = Double.parseDouble(nearRes.getLatitude());
                                                                longitude = Double.parseDouble(nearRes.getLongitude());
                                                                description = nearRes.getDescription();
                                                            } else {
                                                                Toast.makeText(NowReservTicket.this, nearRes.getMessage(), Toast.LENGTH_LONG).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(NowReservTicket.this, "통신에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        try {
                                                            Thread.sleep(500);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }


                                                        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                                                        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기

                                                        serviceApi = new RestRequestHelper().getApiService();
                                                        final Call<requestDeleteResult> ress = serviceApi.requestDelete(sharedToken, _id);

                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    rd = ress.execute().body();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }).start();

                                                        try {
                                                            Thread.sleep(500);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (rd != null) {

                                                            if (rd.getResult().equals("success")) { // 삭제 성공시
                                                                Toast.makeText(getApplicationContext(), rd.getMessage(), Toast.LENGTH_SHORT).show();

                                                            } else { // 삭제 실패시
                                                                Toast.makeText(getApplicationContext(), rd.getMessage(), Toast.LENGTH_SHORT).show();

                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "서버와 통신하지 못했습니다.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        final Call<LocationInfoList> resss = serviceApi.getLocationInfo(nearId);

                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    locationRes = resss.execute().body();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }).start();

                                                        try {
                                                            Thread.sleep(500);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (locationRes != null) {
                                                            if (locationRes.getLocationInfo() != null) {
                                                                locationStartTime = locationRes.getLocationInfo().getPossibleStartTime();
                                                                locationEndTime = locationRes.getLocationInfo().getPossibleEndTime();
                                                                ArrayList<Integer> locationDaySet = (ArrayList<Integer>) locationRes.getLocationInfo().getTimeState();

                                                                for (int i = 0; i < locationDaySet.size(); i++) {
                                                                    timeArray[i] = locationDaySet.get(i);
                                                                }

                                                            } else {
                                                                Toast.makeText(NowReservTicket.this, "주차 공간이 없습니다.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(NowReservTicket.this, "서버와 통신이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        intent.putExtra("timeArray", timeArray);
                                                        intent.putExtra("startTime", locationStartTime);
                                                        intent.putExtra("endTime", locationEndTime);
                                                        intent.putExtra("locationId", nearId);
                                                        intent.putExtra("parkingInfo", parkingInfo);
                                                        intent.putExtra("locationName", locationName);
                                                        intent.putExtra("longitude",longitude);
                                                        intent.putExtra("latitude",latitude);
                                                        intent.putExtra("description",description);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
                                                        String sharedToken = sf.getString("token", "");// data/data/shared_prefs/token파일에서 key="token"가져오기

                                                        ApiService serviceApi = new RestRequestHelper().getApiService();
                                                        final Call<requestDeleteResult> res = serviceApi.requestDelete(sharedToken, _id);

                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    rd = res.execute().body();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }).start();

                                                        try {
                                                            Thread.sleep(500);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (rd.getResult().equals("success")) { // 삭제 성공시
                                                            Toast.makeText(getApplicationContext(), rd.getMessage(), Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);

                                                        } else { // 삭제 실패시
                                                            Toast.makeText(getApplicationContext(), rd.getMessage(), Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);
                                                        }


                                                    }
                                                });
                                        dialog2.create();
                                        dialog2.show();
                                    }
                                })
                                .setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        dialog.create();
                        dialog.show();
                        break;


                }
            }
        };
        btn_back_reserv_ticket.setOnClickListener(Listener);
        btn_reserv_ticket_payment_cancel.setOnClickListener(Listener);
        btn_reserv_ticket_time_extension.setOnClickListener(Listener);
        illegal.setOnClickListener(Listener);
    }
}
