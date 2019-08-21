package com.example.cabway.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.example.core.requestModels.ResetPasswordModel;
import com.example.core.requestModels.VerifyOtpRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;
import com.example.database.Utills.AppConstants;

public class ForgotPasswordRepository {

    private static ForgotPasswordRepository instance;

    public static ForgotPasswordRepository getInstance() {
        if (instance == null) {
            instance = new ForgotPasswordRepository();

        }
        return instance;
    }

    public void getOtp(MutableLiveData<JsonResponse> response, String mobileNo) {
        ApiExecutor.getOtpForForgetPassword(response, mobileNo);
    }

    public void verifyOtp(MutableLiveData<JsonResponse> response, String mobileNo, int enteredOtp) {
        VerifyOtpRequestModel verifyOtpRequestModel = new VerifyOtpRequestModel();
        verifyOtpRequestModel.setMobileNo(mobileNo);
        verifyOtpRequestModel.setOtp(enteredOtp);
        verifyOtpRequestModel.setOtpType(AppConstants.FORGOT_PASSWORD_OTP_TYPE);
        ApiExecutor.verifyOTP(response, verifyOtpRequestModel);
    }

    public void resetPassword(MutableLiveData<JsonResponse> resetPasswordResponse, String mobileNo, String password){
        ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
        resetPasswordModel.setMobileNo(mobileNo);
        resetPasswordModel.setPassword(password);
        ApiExecutor.resetPassword(resetPasswordResponse, resetPasswordModel);
    }


}
