package com.example.core.CommonModels;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class CityModel implements Serializable {

    private int cityId;
    private String name;
    private StateModel state;

    public CityModel(int cityId) {
        this.cityId = cityId;
    }
    public CityModel(String name) {
        this.name = name;
    }

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

    @Override
    public int hashCode() {
        return cityId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        CityModel cityModel = (CityModel) obj;
        return (this.cityId == cityModel.cityId || (cityModel.name != null && this.name != null && this.name.equals(cityModel.name)));
    }
}
