package com.example.cabway.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.core.requestModels.LoginRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;

public class LoginRepository {

    private static LoginRepository instance;

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();

        }
        return instance;
    }

    private LoginRequestModel createLoginRequestModel(String mobileNo, String password, String role){
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setMobileNo(mobileNo);
        loginRequestModel.setPassword(password);
        loginRequestModel.setRole(role);
        return loginRequestModel;
    }

    public void validateLogin(MutableLiveData<JsonResponse> loginResponse, String mobileNo, String password, String role) {
        LoginRequestModel loginRequestModel = createLoginRequestModel(mobileNo, password, role);
        ApiExecutor.validateLogin(loginResponse, loginRequestModel);
    }
}
