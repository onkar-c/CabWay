package com.example.core;


import com.example.core.CommonModels.UserModel;
import com.example.core.requestModels.VerifyOtpRequestModel;
import com.example.core.responseModel.JsonResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("db")
    Call<JsonResponse> getHeros();

    @POST("requestOtp")
    Call<JsonResponse> getOtpForRegistration(@Query("mobileNo") String mobileNo);

    @POST("verifyOtp")
    Call<JsonResponse> verifyOTP(@Body VerifyOtpRequestModel verifyOtpRequestModel);

    @POST("users")
    Call<JsonResponse> registerUser(@Body UserModel userModel);
}
