package com.example.core.CommonModels;

import java.io.Serializable;

public class CityModel implements Serializable {

    private int cityId;
    private String name;
    private StateModel state;


    public CityModel(int cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStateCode() {
        return getState().getId();
    }

    public StateModel getState() {
        return state;
    }
}
