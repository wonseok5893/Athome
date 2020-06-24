package com.example.athome.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athome.R;
import com.example.athome.retrofit.RestRequestHelper;
import com.example.athome.retrofit.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class UsersListActivity extends AppCompatActivity {

    private String result;
    private List<AllUserData> data = null;

    Button backButton;
    ListView usersListView;
    SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_users_listview);

        //모든 정보 다받아옴
        SharedPreferences sf = getSharedPreferences("token", MODE_PRIVATE);
        String sharedToken = sf.getString("token", "");

        ApiService apiService = new RestRequestHelper().getApiService();
        final Call<AllUserResult> res = apiService.getAllUsers(sharedToken,"good");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AllUserResult allUserResult = res.execute().body();
                    result = allUserResult.getResult();
                    data = allUserResult.getData();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
//------------------------------------------------------------------------------------------


        usersListView=(ListView)findViewById(R.id.admin_users_listView);
        searchView=(SearchView)findViewById(R.id.userTextFilter);

        backButton=(Button)findViewById(R.id.admin_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });

        int totalElements = data.size();// arrayList의 요소의 갯수를 구한다.
        for (int index = 0; index < totalElements; index++) {
            System.out.println(data.get(index));
        }



        //리스트 속의 아이템 연결
        UserListAdapter adapter=new UserListAdapter(this,R.layout.admin_users_data, (ArrayList<AllUserData>) data);
        usersListView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("junggyu", "검색어" + newText);
                if (newText.length() > 0) {
                    usersListView.setFilterText(newText) ;
                } else {
                    usersListView.clearTextFilter() ;
                }
                return false;
            }
        });

        //아이템 클릭시 작동
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AdminUserListClicked.class);
                intent.putExtra("userInfo",data.get(position));
                //putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값

                startActivity(intent);
            }
        });
    }

    public List<AllUserData> getData() {
        return data;
    }
}