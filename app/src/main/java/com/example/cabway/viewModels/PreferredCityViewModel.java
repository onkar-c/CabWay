package com.example.cabway.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cabway.repositories.PreferredCityRepository;
import com.example.core.responseModel.JsonResponse;

public class PreferredCityViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> preferredCityResponse;
    private PreferredCityRepository preferredCityRepository;

    public void init() {
        preferredCityResponse = new MutableLiveData<>();
        preferredCityRepository = PreferredCityRepository.getInstance();
    }

    public MutableLiveData<JsonResponse> getPreferredCityResponseMld() {
        return preferredCityResponse;
    }

    public PreferredCityRepository getPreferredCityRepository() {
        return preferredCityRepository;
    }
}