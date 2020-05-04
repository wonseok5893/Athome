package com.example.athome.retrofit;


import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    @FormUrlEncoded
    @POST("api/auth")
    Call<AuthResult> authenticate(@Header("x-access-token") String token
            ,@Field("authKey") String key);

    String API_NAVER_URL = "https://naveropenapi.apigw.ntruss.com/";

    //@Headers -> 헤더에 키값 넘김 (윤지원 05-04)
    @Headers({"X-NCP-APIGW-API-KEY-ID:25z9e189v0", "X-NCP-APIGW-API-KEY:GfIyHLDau8VSS1mok1CdYYekGApHr3UGIg8YHxem"})
    //@Get -> uri 보낼 주소 , @Query로 좌표값 보냄 (윤지원 05-04)
    @GET("map-direction/v1/driving")
    Call<NaviResult> getNavigate(@Query(value = "start",encoded = true) String start,
                                 @Query(value = "goal",encoded = true) String goal);

//    Call<JsonArray> getUserRepositories(@Path("user") String userName);
}