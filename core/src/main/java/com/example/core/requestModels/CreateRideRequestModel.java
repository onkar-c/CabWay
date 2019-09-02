package com.example.core.requestModels;

import com.example.core.CommonModels.CityModel;
import com.google.gson.annotations.SerializedName;

public class CreateRideRequestModel {

    @SerializedName("agencyMobileNo")
    private String agencyMobileNo;

    @SerializedName("carType")
    private String carType;

    @SerializedName("cost")
    private int cost;

    @SerializedName("distance")
    private int distance;

    @SerializedName("dropTime")
    private String dropTime;

    @SerializedName("fromCity")
    private CityModel fromCity;

    @SerializedName("driverId")
    private int driverId;

    @SerializedName("pickupTime")
    private String pickupTime;

    @SerializedName("rideType")
    private String rideType;

    @SerializedName("toCity")
    private CityModel toCity;


    public void setAgencyMobileNo(String agencyMobileNo) {
        this.agencyMobileNo = agencyMobileNo;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public void setFromCity(CityModel fromCity) {
        this.fromCity = fromCity;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    public void setToCity(CityModel toCity) {
        this.toCity = toCity;
    }
}
