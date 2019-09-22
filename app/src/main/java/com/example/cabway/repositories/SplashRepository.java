package com.example.cabway.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;

public class SplashRepository {

    private static SplashRepository instance;

    public static SplashRepository getInstance() {
        if (instance == null) {
            instance = new SplashRepository();

        }
        return instance;
    }


    public void getStateCity(MutableLiveData<JsonResponse> stateCityResponse, String dataVersion) {
        ApiExecutor.getStateAndCity(stateCityResponse, dataVersion);
    }
}
