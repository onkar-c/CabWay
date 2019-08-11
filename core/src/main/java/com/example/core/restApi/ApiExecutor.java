package com.example.core.restApi;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.core.ApiClient;
import com.example.core.ApiInterface;
import com.example.core.RequestExecutor;
import com.example.core.Utills.HeroUtils;
import com.example.core.requestModels.VerifyOtpRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.database.models.UserModel;

import java.util.Objects;

public class ApiExecutor {

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
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestExecutor.ExecuteApi(apiInterface.getHeros(), jsonResponseMutableLiveData);
    }

    public static void getOtpForRegistration(final MutableLiveData<JsonResponse> mltOtpResponse, String mobileNo) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestExecutor.ExecuteApi(apiInterface.getOtpForRegistration(mobileNo), mltOtpResponse);
    }

    public static void verifyOTP(final MutableLiveData<JsonResponse> mltVerifyOtpResponse, VerifyOtpRequestModel verifyOtpRequestModel) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestExecutor.ExecuteApi(apiInterface.verifyOTP(verifyOtpRequestModel), mltVerifyOtpResponse);
    }

    public static void registerUser(final MutableLiveData<JsonResponse> mltRegisterUserResponse, com.example.core.CommonModels.UserModel userModel) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        RequestExecutor.ExecuteApi(apiInterface.registerUser(userModel), mltRegisterUserResponse);
    }
}
