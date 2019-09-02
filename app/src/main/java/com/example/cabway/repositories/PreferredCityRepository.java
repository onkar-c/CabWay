package com.example.cabway.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.core.CommonModels.CityModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;

public class PreferredCityRepository {

    private static PreferredCityRepository instance;

    public static PreferredCityRepository getInstance() {
        if (instance == null) {
            instance = new PreferredCityRepository();

        }
        return instance;
    }

    public void updatePreferredCity(MutableLiveData<JsonResponse> preferredCityResponse, CityModel cityModel) {
        ApiExecutor.updatePreferredCity(preferredCityResponse, cityModel);
    }
}