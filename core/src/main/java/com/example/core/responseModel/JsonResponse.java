package com.example.core.responseModel;

import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.DocumentModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.CommonModels.UserModel;
import com.example.core.CommonModels.VehicleTypeModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JsonResponse implements Serializable {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("heroes")
    private List<HeroesModel> heroesList;

    @SerializedName("user")
    private UserModel user;

    @SerializedName("listDocument")
    private List<DocumentModel> documentsList;

    @SerializedName("documentCompleted")
    private boolean documentCompleted;

    @SerializedName("stateList")
    private List<StateModel> stateList;

    @SerializedName("cityList")
    private List<CityModel> cityList;

    @SerializedName("carType")
    private List<VehicleTypeModel> vehicleList;

    @SerializedName("rideList")
    private List<RideResponseModel> rideList;

    @SerializedName("acceptedRideList")
    private List<RideResponseModel> acceptedRideList;

    @SerializedName("requestedRideList")
    private List<RideResponseModel> requestedRideList;

    @SerializedName("ride")
    private RideResponseModel ride;

    private String serverVersion;

    public String getServerVersion() {
        return serverVersion;
    }

    public List<HeroesModel> getHeroesList() {
        return heroesList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUser() {
        return user;
    }

    public List<DocumentModel> getDocumentsList() {
        return documentsList;
    }

    public boolean isDocumentCompleted() {
        return documentCompleted;
    }

    public List<StateModel> getStateList() {
        return stateList;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public List<VehicleTypeModel> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleTypeModel> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<RideResponseModel> getRideList() {
        return rideList;
    }

    public List<RideResponseModel> getAcceptedRideList() {
        return acceptedRideList;
    }

    public List<RideResponseModel> getRequestedRideList() {
        return requestedRideList;
    }

    public RideResponseModel getRide() {
        return ride;
    }
}
