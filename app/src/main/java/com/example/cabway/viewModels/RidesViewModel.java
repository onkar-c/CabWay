package com.example.cabway.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cabway.repositories.RidesRepository;
import com.example.core.responseModel.JsonResponse;

public class RidesViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> createRideResponse;
    private MutableLiveData<JsonResponse> deleteRideResponse;
    private MutableLiveData<JsonResponse> ridesResponse;
    private MutableLiveData<JsonResponse> ridesHistoryResponse;
    private MutableLiveData<JsonResponse> acceptRejectRidesResponse;
    private RidesRepository ridesRepository;
    private MutableLiveData<JsonResponse> requestRideResponse;

    public void init() {
        createRideResponse = new MutableLiveData<>();
        ridesResponse = new MutableLiveData<>();
        acceptRejectRidesResponse = new MutableLiveData<>();
        deleteRideResponse = new MutableLiveData<>();
        requestRideResponse = new MutableLiveData<>();
        ridesHistoryResponse = new MutableLiveData<>();
        ridesRepository = RidesRepository.getInstance();
    }

    public MutableLiveData<JsonResponse> getCreateRideMld() {
        return createRideResponse;
    }

    public MutableLiveData<JsonResponse> getRidesMld() {
        return ridesResponse;
    }

    public RidesRepository getRidesRepository() {
        return ridesRepository;
    }

    public MutableLiveData<JsonResponse> getAcceptRejectRidesResponseMld() {
        return acceptRejectRidesResponse;
    }

    public MutableLiveData<JsonResponse> getDeleteRideResponseMld() {
        return deleteRideResponse;
    }

    public MutableLiveData<JsonResponse> getRequestRideResponseMld() {
        return requestRideResponse;
    }

    public MutableLiveData<JsonResponse> getRidesHistoryResponseMld() {
        return ridesHistoryResponse;
    }
}
