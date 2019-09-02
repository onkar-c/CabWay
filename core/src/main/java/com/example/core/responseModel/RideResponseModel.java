package com.example.core.responseModel;

import com.example.core.CommonModels.CityModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RideResponseModel implements Serializable {

    @SerializedName("id")
    private int rideId;

    @SerializedName("fromCity")
    private CityModel fromCity;

    @SerializedName("toCity")
    private CityModel toCity;

    @SerializedName("pickupTime")
    private String pickupTime;

    @SerializedName("dropTime")
    private String dropTime;

    @SerializedName("agencyMobileNo")
    private String agencyMobileNo;

    @SerializedName("rideType")
    private String rideType;

    @SerializedName("carType")
    private String carType;

    @SerializedName("distance")
    private Integer distance;

    @SerializedName("cost")
    private Long cost;

    @SerializedName("agency")
    private AgencyResponseModel agency;

    public int getRideId() {
        return rideId;
    }

    public CityModel getFromCity() {
        return fromCity;
    }

    public CityModel getToCity() {
        return toCity;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public String getDropTime() {
        return dropTime;
    }

    public String getAgencyMobileNo() {
        return agencyMobileNo;
    }

    public String getRideType() {
        return rideType;
    }

    public String getCarType() {
        return carType;
    }

    public Integer getDistance() {
        return distance;
    }

    public Long getCost() {
        return cost;
    }

    public AgencyResponseModel getAgency() {
        return agency;
    }
}
