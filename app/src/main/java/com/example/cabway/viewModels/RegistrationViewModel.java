package com.example.cabway.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.cabway.repositories.RegistrationRepository;
import com.example.core.responseModel.JsonResponse;

public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<JsonResponse> otpResponse;
    private MutableLiveData<JsonResponse> verifyOtpResponse;
    private MutableLiveData<JsonResponse> userRegistrationResponse;
    private RegistrationRepository registrationRepository;

    public void init() {
        otpResponse = new MutableLiveData<>();
        verifyOtpResponse = new MutableLiveData<>();
        userRegistrationResponse = new MutableLiveData<>();
        registrationRepository = RegistrationRepository.getInstance();
    }

    public MutableLiveData<JsonResponse> getOtpResponseMld() {
        return otpResponse;
    }

    public MutableLiveData<JsonResponse> getVerifyOtpResponseMld() {
        return verifyOtpResponse;
    }

    public MutableLiveData<JsonResponse> getUserRegistrationResponseMld() {
        return userRegistrationResponse;
    }

    public RegistrationRepository getRegistrationRepository() {
        return registrationRepository;
    }
}