package com.example.cabway.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.example.core.CommonModels.UserModel;
import com.example.core.requestModels.VerifyOtpRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;
import com.example.database.Utills.AppConstants;

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

    public void verifyOtp(MutableLiveData<JsonResponse> response, String mobileNo, int enteredOtp) {
        VerifyOtpRequestModel verifyOtpRequestModel = new VerifyOtpRequestModel();
        verifyOtpRequestModel.setMobileNo(mobileNo);
        verifyOtpRequestModel.setOtp(enteredOtp);
        verifyOtpRequestModel.setOtpType(AppConstants.REGISTRATION_OTP_TYPE);
        ApiExecutor.verifyOTP(response, verifyOtpRequestModel);
    }

    public void registerUser(MutableLiveData<JsonResponse> registerUserResponse, UserModel userModel, String filePath) {
        ApiExecutor.registerUser(registerUserResponse, userModel, filePath);
    }
}