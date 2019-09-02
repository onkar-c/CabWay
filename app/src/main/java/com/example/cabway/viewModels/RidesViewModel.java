package com.example.cabway.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cabway.repositories.RidesRepository;
import com.example.core.responseModel.JsonResponse;

public class RidesViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> createRideResponse;
    private MutableLiveData<JsonResponse> ridesResponse;
    private RidesRepository ridesRepository;

    public void init(){
        createRideResponse=new MutableLiveData<>();
        ridesResponse=new MutableLiveData<>();
        ridesRepository = RidesRepository.getInstance();
    }

    public MutableLiveData<JsonResponse> getCreateRideMld(){
        return createRideResponse;
    }

    public MutableLiveData<JsonResponse> getRidesMld(){
        return ridesResponse;
    }

    public RidesRepository getRidesRepository(){
        return ridesRepository;
    }
}
