package com.example.athome.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.athome.R;
import com.example.athome.notice.ItemNoticeData;

import java.util.ArrayList;

public class AccountCardList extends Activity {

    private Button btn_back_card_list;
    private Button btn_card_register;
    private ListView card_listView= null;
    private ArrayList<ItemAccountCardData> data=null;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.account_card_list);

        this.InitializeView();
        this.SetListner();

        ItemAccountCardData data1=new ItemAccountCardData("신한은행",1234,5678,0012,3344,12,23);
        data.add(data1);

        //리스트 속의 아이템 연결
        CardListAdapter adapter=new CardListAdapter(this,R.layout.account_card_listview_item,data);
        card_listView.setAdapter(adapter);

    }

    public void InitializeView(){
        btn_back_card_list=(Button)findViewById(R.id.btn_back_card_list);
        btn_card_register=(Button)findViewById(R.id.btn_card_register);
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
                    case R.id.btn_card_register: //등록하기 버튼
                        Intent intent = new Intent(getApplicationContext(), AccountCardRegister.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        btn_card_register.setOnClickListener(Listener);
        btn_back_card_list.setOnClickListener(Listener);
    }

}
