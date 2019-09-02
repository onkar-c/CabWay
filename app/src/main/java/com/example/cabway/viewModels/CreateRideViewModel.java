package com.example.cabway.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cabway.repositories.CreateRideRepository;
import com.example.core.responseModel.JsonResponse;

public class CreateRideViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> createRideResponse;
    private CreateRideRepository createRideRepository;

    public void init(){
        createRideResponse=new MutableLiveData<>();
        createRideRepository= CreateRideRepository.getInstance();
    }

    public MutableLiveData<JsonResponse> getCreateRideMld(){
        return createRideResponse;
    }

    public CreateRideRepository getCreateRideRepository(){
        return createRideRepository;
    }
}
