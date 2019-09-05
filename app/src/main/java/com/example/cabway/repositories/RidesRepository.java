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

    public void createRide(MutableLiveData<JsonResponse> createRideResponse, CreateRideRequestModel createRideRequestModel, boolean isUpdate){
        ApiExecutor.createUpdateRide(createRideResponse,createRideRequestModel, isUpdate);
    }

    public void getRides(MutableLiveData<JsonResponse> ridesResponse){
        ApiExecutor.getRides(ridesResponse);
    }

    public void acceptRejectRide(MutableLiveData<JsonResponse> acceptRejectRidesResponse, Long rideId, int userId, String action){
        ApiExecutor.acceptRejectRides(rideId, userId, action, acceptRejectRidesResponse);
    }

    public void deleteRide(MutableLiveData<JsonResponse> deleteRideResponse, Long rideId){
        ApiExecutor.deleteRide(deleteRideResponse,rideId);
    }

    public void requestRide(MutableLiveData<JsonResponse> requestRideResponse, Long rideId){
        ApiExecutor.requestRide(requestRideResponse,rideId);
    }
}
