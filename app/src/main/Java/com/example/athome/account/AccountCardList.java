package com.example.athome.account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.athome.R;
import com.example.athome.notice.ItemNoticeData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AccountCardList extends Activity {

    private Button  btn_back_card_list;
    private FloatingActionButton btn_card_register;
    private ListView card_listView= null;
    private ArrayList<ItemAccountCardData> data=null;
    private CardListAdapter adapter;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_card_list);

        this.InitializeView();
        this.SetListner();

        ItemAccountCardData data1=new ItemAccountCardData("신한은행",1234,5678,0012,3344,12,23);
        data.add(data1);

        //리스트 속의 아이템 연결
        adapter=new CardListAdapter(this,R.layout.account_card_listview_item,data);
        card_listView.setAdapter(adapter);

        card_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(AccountCardList.this)
                        .setTitle("카드 삭제")
                        .setMessage("해당 카드를 삭제하시겠습니까?")
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
        btn_back_card_list=(Button)findViewById(R.id.btn_back_card_list);
        btn_card_register=(FloatingActionButton)findViewById(R.id.btn_card_register);
        card_listView=(ListView)findViewById(R.id.card_listView);
        data=new ArrayList<>();
    }


    public void SetListner()
    {
        View.OnClickListener Listener= new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                switch (v.getId()){
                    case R.id.btn_back_card_list: //뒤로가기 버튼
                        finish();
                        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
                        break;
                    case R.id.btn_card_register: //등록하기  버튼
                        // 다이얼로그를 생성. 사용자가 만든 클래스이다.
                        AccountCardRegister accountCardRegister= new AccountCardRegister(AccountCardList.this);
                        // 다이얼로그 호출
                        //그외 작업
                        accountCardRegister.callFunction();
                        break;
                }
            }
        };
        btn_back_card_list.setOnClickListener(Listener);
        btn_card_register.setOnClickListener(Listener);

    }

}
