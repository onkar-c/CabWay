package com.example.cabway.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;

public class RegistrationRepository {
    private static RegistrationRepository instance;

    public static RegistrationRepository getInstance() {
        if (instance == null) {
            instance = new RegistrationRepository();

        }
        return instance;
    }

    public void getOtp(MutableLiveData<JsonResponse> response, String mobileNo) {
        ApiExecutor.getOtpForRegistration(response, mobileNo);
    }
}