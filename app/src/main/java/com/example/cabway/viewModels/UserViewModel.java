package com.example.cabway.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cabway.repositories.UserRepository;
import com.example.core.responseModel.JsonResponse;

public class UserViewModel extends ViewModel {

    private MutableLiveData<JsonResponse> otpResponse;
    private MutableLiveData<JsonResponse> verifyOtpResponse;
    private MutableLiveData<JsonResponse> userRegistrationResponse;
    private MutableLiveData<JsonResponse> userUpdateResponse;
    private UserRepository userRepository;

    public void init() {
        otpResponse = new MutableLiveData<>();
        verifyOtpResponse = new MutableLiveData<>();
        userRegistrationResponse = new MutableLiveData<>();
        userUpdateResponse = new MutableLiveData<>();
        userRepository = UserRepository.getInstance();
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

    public MutableLiveData<JsonResponse> getUserUpdateResponseMld() {
        return userUpdateResponse;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}