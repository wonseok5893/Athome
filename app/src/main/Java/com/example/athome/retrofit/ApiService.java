package com.example.athome.retrofit;


import com.example.athome.admin.AllUserResult;
import com.example.athome.admin.UsersListActivity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @FormUrlEncoded
    @POST("user/join")
    Call<RegisterResult> signUp(@Field("userId") String userId,
                                @Field("userPassword") String userPassword,
                                @Field("userName") String userName,
                                @Field("userEmail") String userEmail,
                                @Field("userPhone") String userPhone
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResult> login(@Field("userId") String userId,
                            @Field("userPassword") String userPassword);

    @FormUrlEncoded
    @POST("api/auth")
    Call<AuthResult> authenticate(@Header("x-access-token") String token
            , @Field("authKey") String key);

    @FormUrlEncoded
    @POST("user/editPassword")
    Call<EditResult> editPassword(@Header("x-access-token") String token,
                                  @Field("userPassword") String userPassword,
                                  @Field("newUserPassword") String newUserPassword);

    @FormUrlEncoded
    @POST("api/reservation/enroll")
    Call<ResponseBody> sendReserve(@Header("x-access-token") String token,
                                   @Field("carNum") String carNum,
                                   @Field("phNum") String phNum,
                                   @Field("startTime") Long startTime,
                                   @Field("endTime") Long endTime);

    @FormUrlEncoded
    @POST("admin/users")
    Call<AllUserResult> getAllUsers(@Header("x-access-token") String token
            , @Field("secret") String secret);


    @Multipart
    @POST("api/reservation/enroll")
    Call<EnrollResult> postRegister(@Header("x-access-token") String token,
                                    @Part MultipartBody.Part image,
                                    @Part("phNum") String phNum,
                                    @Part("birth") String birth,
                                    @Part("carNum") String carNum
    );

//    Call<JsonArray> getUserRepositories(@Path("user") String userName);
}