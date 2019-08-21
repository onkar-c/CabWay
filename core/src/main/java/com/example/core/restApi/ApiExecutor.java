package com.example.core.restApi;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.core.ApiClient;
import com.example.core.ApiInterface;
import com.example.core.RequestExecutor;
import com.example.core.Utills.FileUtils;
import com.example.core.Utills.HeroUtils;
import com.example.core.requestModels.LoginRequestModel;
import com.example.core.requestModels.ResetPasswordModel;
import com.example.core.requestModels.VerifyOtpRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.database.models.UserModel;
import com.google.gson.Gson;

import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ApiExecutor {

    private static ApiInterface getApiInterface(){
         return ApiClient.getClient().create(ApiInterface.class);
    }

    public static void getUsersFromServer(final MutableLiveData<Boolean> response, final Context context) {
        MutableLiveData<JsonResponse> jsonResponseMutableLiveData = new MutableLiveData<>();
        jsonResponseMutableLiveData
                .observe((LifecycleOwner) context, new Observer<JsonResponse>() {
                    @Override
                    public void onChanged(@Nullable JsonResponse s) {
                        new UserModel(context).insertUsers(HeroUtils.convertHeroModelLitsToHeroList(Objects.requireNonNull(s).getHeroesList()), null);
                        response.setValue(true);
                    }
                });
        RequestExecutor.ExecuteApi(getApiInterface().getHeros(), jsonResponseMutableLiveData);
    }

    public static void getOtpForRegistration(final MutableLiveData<JsonResponse> mltOtpResponse, String mobileNo) {
        RequestExecutor.ExecuteApi(getApiInterface().getOtpForRegistration(mobileNo), mltOtpResponse);
    }

    public static void getOtpForForgetPassword(final MutableLiveData<JsonResponse> mltOtpResponse, String mobileNo) {
        RequestExecutor.ExecuteApi(getApiInterface().getOtpForForgetPassword(mobileNo), mltOtpResponse);
    }

    public static void verifyOTP(final MutableLiveData<JsonResponse> mltVerifyOtpResponse, VerifyOtpRequestModel verifyOtpRequestModel) {
        RequestExecutor.ExecuteApi(getApiInterface().verifyOTP(verifyOtpRequestModel), mltVerifyOtpResponse);
    }

    public static void resetPassword(final MutableLiveData<JsonResponse> mltResetPassword, ResetPasswordModel resetPasswordModel){
        RequestExecutor.ExecuteApi(getApiInterface().resetPassword(resetPasswordModel), mltResetPassword);
    }

    public static void registerUser(final MutableLiveData<JsonResponse> mltRegisterUserResponse, com.example.core.CommonModels.UserModel userModel, String filePath) {
        RequestBody user = RequestBody.create(MultipartBody.FORM, new Gson().toJson(userModel));
        MultipartBody.Part body = FileUtils.getMultipartBody(filePath);
        RequestExecutor.ExecuteApi(getApiInterface().register(body, user), mltRegisterUserResponse);
    }

    public static void validateLogin(final MutableLiveData<JsonResponse> mltLoginResponse, LoginRequestModel loginRequestModel) {
        RequestExecutor.ExecuteApi(getApiInterface().validateLogin(loginRequestModel), mltLoginResponse);
    }
}
