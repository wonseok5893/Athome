package com.example.athome.account;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.athome.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class AccountCarList extends Activity {
    private Button btn_back_car_list;
    private FloatingActionButton btn_car_register;
    private ListView car_listView;
    private ArrayList<ItemAccountCarData> data=null;
    private TextView car_number_delete;
    private CarListAdapter adapter;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_car_list);

        this.InitializeView();
        this.SetListner();

        ItemAccountCarData carnumber1=new ItemAccountCarData("11가1234");
        ItemAccountCarData carnumber2=new ItemAccountCarData("34마5377");
        ItemAccountCarData carnumber3=new ItemAccountCarData("81다4212");
        data.add(carnumber1);
        data.add(carnumber2);
        data.add(carnumber3);


        //리스트 속의 아이템 연결
       adapter=new CarListAdapter(this,R.layout.account_car_listview_item,data);
       car_listView.setAdapter(adapter);

       car_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               new AlertDialog.Builder(AccountCarList.this)
                       .setTitle("차량번호 삭제")
                       .setMessage("해당 차량번호를 삭제하시겠습니까?")
                       .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               data.remove(position);
                               adapter.notifyDataSetChanged();
                           }
                       })
                       .setNegativeButton("취소",null)
                       .show();

               return;
           }
       });

    }
    public void InitializeView(){
        btn_back_car_list=(Button)findViewById(R.id.btn_back_car_list);
        btn_car_register=(FloatingActionButton)findViewById(R.id.btn_car_register);
        car_listView=(ListView)findViewById(R.id.car_listView);
        data=new ArrayList<>();

    }

    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_car_list: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.btn_car_register: //등록하기  버튼
                        // 다이얼로그를 생성. 사용자가 만든 클래스이다.
                        AccountCarRegister accountCarRegister= new AccountCarRegister(AccountCarList.this);
                        // 다이얼로그 호출
                        //그외 작업
                        accountCarRegister.callFunction();
                        break;
                }
            }
        };
        btn_back_car_list.setOnClickListener(Listener);
        btn_car_register.setOnClickListener(Listener);
    }

}