package com.example.athome;

import android.util.Log;

import com.example.athome.retrofit.ApiService;
import com.example.athome.retrofit.AuthResult;
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
    String token;
    public User(){};
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


        //받아온거 뽑아내기 (동기처리)
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

    //로그인 인증
    public String login() {

        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<LoginResult> res = serviceApi.login(userId, userPassword);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginResult loginResult = res.execute().body();
                    loginRes = loginResult.getLoginResult();
                    token = loginResult.getToken();


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

    //토큰 인증
    public boolean authenticate(String sharedToken) {
        ApiService serviceApi = new RestRequestHelper().getApiService();
        final Call<AuthResult> res = serviceApi.authenticate(sharedToken, "wonseok");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final AuthResult authResult = res.execute().body();
                    //User에 담는다 받은 결과를
                    userId = authResult.getUserId();
                    userEmail = authResult.getUserEmail();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(100);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //검증 결과 리턴
        if (userId != null)
            return true;
        else {
            return false;
        }
    }

}