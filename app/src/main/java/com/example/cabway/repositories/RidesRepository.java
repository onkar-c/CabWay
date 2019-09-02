package com.example.cabway.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.core.requestModels.CreateRideRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;

public class RidesRepository {

    private static RidesRepository instance;

    public static RidesRepository getInstance(){
        if(instance==null){
            instance=new RidesRepository();
        }
        return instance;
    }

    public void createRide(MutableLiveData<JsonResponse> createRideResponse, CreateRideRequestModel createRideRequestModel){
        ApiExecutor.createRide(createRideResponse,createRideRequestModel);
    }

    public void getRides(MutableLiveData<JsonResponse> ridesResponse){
        ApiExecutor.getRides(ridesResponse);
    }
}
