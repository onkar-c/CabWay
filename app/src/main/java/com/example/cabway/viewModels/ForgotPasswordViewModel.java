package com.example.cabway.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cabway.repositories.ForgotPasswordRepository;
import com.example.core.responseModel.JsonResponse;

public class ForgotPasswordViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> otpResponse;
    private MutableLiveData<JsonResponse> verifyOtpResponse;
    private MutableLiveData<JsonResponse> resetPasswordResponse;

    public MutableLiveData<JsonResponse> getResetPasswordResponseMld() {
        return resetPasswordResponse;
    }

    private ForgotPasswordRepository forgotPasswordRepository;

    public void init() {
        otpResponse = new MutableLiveData<>();
        verifyOtpResponse = new MutableLiveData<>();
        resetPasswordResponse = new MutableLiveData<>();
        forgotPasswordRepository = ForgotPasswordRepository.getInstance();
    }

    public MutableLiveData<JsonResponse> getOtpResponseMld() {
        return otpResponse;
    }

    public MutableLiveData<JsonResponse> getVerifyOtpResponseMld() {
        return verifyOtpResponse;
    }


    public ForgotPasswordRepository getForgotPasswordRepository() {
        return forgotPasswordRepository;
    }
}
