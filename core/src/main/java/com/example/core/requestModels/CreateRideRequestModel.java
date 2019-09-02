package com.example.core.requestModels;

import com.google.gson.annotations.SerializedName;

public class CreateRideRequestModel {
    @SerializedName("agencyId")
    private int agencyId;

    @SerializedName("agencyMobileNo")
    private String agencyMobileNo;

    @SerializedName("carType")
    private String  carType;

    @SerializedName("cost")
    private int cost;

    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("createdTime")
    private String createdTime;

    @SerializedName("distance")
    private int distance;

    @SerializedName("driverId")
    private int driverId;

    @SerializedName("dropTime")
    private String dropTime;

    @SerializedName("fromCity")
    private int fromCity;

    @SerializedName("id")
    private int id;

    @SerializedName("pickupTime")
    private String pickupTime;

    @SerializedName("rideType")
    private String rideType;

    @SerializedName("status")
    private String status;

    @SerializedName("toCity")
    private int toCity;

    @SerializedName("updatedTime")
    private String updatedTime;

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyMobileNo() {
        return agencyMobileNo;
    }

    public void setAgencyMobileNo(String agencyMobileNo) {
        this.agencyMobileNo = agencyMobileNo;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public int getFromCity() {
        return fromCity;
    }

    public void setFromCity(int fromCity) {
        this.fromCity = fromCity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getToCity() {
        return toCity;
    }

    public void setToCity(int toCity) {
        this.toCity = toCity;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
