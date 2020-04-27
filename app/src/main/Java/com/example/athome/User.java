package com.example.athome;

import android.util.Log;

import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.LoginResult;
import com.example.athome.retrofit.RegisterResult;

import java.io.IOException;

import retrofit2.Call;

public class User {
    private String userId;
    private String userPassword;
    private String userEmail;
    private String userPhone;

    String registerRes;
    String loginRes;

    //로그인시 사용
    public User(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }
    //회우너가입시 사용
    public User(String userId, String userPassword, String userEmail, String userPhone) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String register() {
        ApiService serviceApi = new RestRequestHelper().getApiService();

        final Call<RegisterResult> res = serviceApi.signUp(userId, userPassword, userEmail, userPhone); // register (실제 통신이 이루어지는 곳)


        // 3. 받아온거 뽑아내기 (동기)
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RegisterResult registerResult = res.execute().body();
                    registerRes = registerResult.getRegisterResult();

                    // test log
                    if (registerRes == null) {
                        Log.d("TEST", "Register 통신 실패....");
                    } else if (registerRes.equals("success")) {
                        Log.d("TEST", "Register 성공!");
                    } else if (registerRes.equals("fail")) {
                        Log.d("TEST", "Register 실패.. ");
                    }

                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return registerRes;
    }

    public String login() {
        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<LoginResult> res = serviceApi.login(userId, userPassword);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginResult loginResult = res.execute().body();
                    loginRes = loginResult.getLoginResult();

                    // test log
                    if (loginRes == null) {
                        Log.d("TEST", "로그인 통신 실패....");
                    } else if (loginRes.equals("success")) {
                        Log.d("TEST", "로그인 성공!");
                    } else if (loginRes.equals("fail")) {
                        Log.d("TEST", "로그인 실패.. ");
                    }

                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(100);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return loginRes;
    }


}