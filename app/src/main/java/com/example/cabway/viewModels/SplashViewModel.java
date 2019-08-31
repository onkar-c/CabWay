package com.example.cabway.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cabway.repositories.SplashRepository;
import com.example.core.responseModel.JsonResponse;

public class SplashViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> stateCityResponse;
    private SplashRepository splashRepository;

    public void init() {
        stateCityResponse = new MutableLiveData<>();
        splashRepository = SplashRepository.getInstance();
    }

    public MutableLiveData<JsonResponse> getStateCityResponseMld() {
        return stateCityResponse;
    }

    public SplashRepository getSplashRepository() {
        return splashRepository;
    }

}