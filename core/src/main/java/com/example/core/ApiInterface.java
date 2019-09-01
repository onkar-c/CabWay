package com.example.core;


import com.example.core.CommonModels.CityModel;
import com.example.core.requestModels.LoginRequestModel;
import com.example.core.requestModels.ResetPasswordModel;
import com.example.core.requestModels.VerifyOtpRequestModel;
import com.example.core.responseModel.JsonResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("db")
    Call<JsonResponse> getHeros();

    @POST("requestOtp")
    Call<JsonResponse> getOtpForRegistration(@Query("mobileNo") String mobileNo);

    @GET("resetPasswordOtp")
    Call<JsonResponse> getOtpForForgetPassword(@Query("mobileNo") String mobileNo);

    @POST("verifyOtp")
    Call<JsonResponse> verifyOTP(@Body VerifyOtpRequestModel verifyOtpRequestModel);

    @PUT("users/resetPassword")
    Call<JsonResponse> resetPassword(@Body ResetPasswordModel resetPasswordModel);

    @POST("users/Login")
    Call<JsonResponse> validateLogin(@Body LoginRequestModel loginRequestModel);

    @Multipart
    @POST("users")
    Call<JsonResponse> register(@Part MultipartBody.Part file, @Part("user") RequestBody userModel);

    @Multipart
    @PUT("users/updateUser")
    Call<JsonResponse> updateUser(@Part MultipartBody.Part file, @Part("user") RequestBody userModel);

    @Multipart
    @POST("document")
    Call<JsonResponse> documentUpload(@Part MultipartBody.Part file, @Part("document") RequestBody documentUploadRequestModel);

    @GET("document/list")
    Call<JsonResponse> getDocuments();

    @GET("cabinfo/cityList")
    Call<JsonResponse> getStateAndCity();

    @PUT("users/cityPrefrence")
    Call<JsonResponse> updatePreferredCity(@Body CityModel cityModel);
}
