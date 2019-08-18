package com.example.cabway.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.cabway.repositories.LoginRepository;
import com.example.core.responseModel.JsonResponse;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> loginResponse;
    private LoginRepository loginRepository;

    public void init() {
        loginResponse = new MutableLiveData<>();
        loginRepository = LoginRepository.getInstance();
    }

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public MutableLiveData<JsonResponse> getLoginResponse() {
        return loginResponse;
    }
}
