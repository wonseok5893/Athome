package com.example.athome.retrofit;



import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("user/join")
    Call<RegisterResult> signUp(@Field("userId") String userName,
            @Field("userPassword") String userPassword,
            @Field("userEmail") String userEmail,
            @Field("userPhone") String userPhone);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResult> login(@Field("userId") String userId,
                            @Field("userPassword") String userPassword);


//    Call<JsonArray> getUserRepositories(@Path("user") String userName);
}
