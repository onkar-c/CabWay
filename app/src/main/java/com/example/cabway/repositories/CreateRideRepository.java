package com.example.cabway.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.core.requestModels.CreateRideRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;

public class CreateRideRepository {

    private static CreateRideRepository instance;

    public static CreateRideRepository getInstance(){
        if(instance==null){
            instance=new CreateRideRepository();
        }
        return instance;
    }

    public void createRide(MutableLiveData<JsonResponse> createRideResponse, CreateRideRequestModel createRideRequestModel){
        ApiExecutor.createRide(createRideResponse,createRideRequestModel);
    }
}
