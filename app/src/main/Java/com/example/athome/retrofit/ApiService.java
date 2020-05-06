package com.example.athome.retrofit;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("user/join")
    Call<RegisterResult> signUp(@Field("userId") String userId,
                                @Field("userPassword") String userPassword,
                                @Field("userName") String userName,
                                @Field("userEmail") String userEmail,
                                @Field("userPhone") String userPhone,
                                @Field("userCarNumber") String userCarNumber);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResult> login(@Field("userId") String userId,
                            @Field("userPassword") String userPassword);
    @FormUrlEncoded
    @POST("api/auth")
    Call<AuthResult> authenticate(@Header("x-access-token") String token
            ,@Field("authKey") String key);



//    Call<JsonArray> getUserRepositories(@Path("user") String userName);
}