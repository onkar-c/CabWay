package com.example.core.CommonModels;

import java.util.ArrayList;

public class StateModel {

    private String id;
    private String name;
    private ArrayList<CityModel> cityArr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CityModel> getCityArr() {
        return cityArr;
    }

    public void setCityArr(ArrayList<CityModel> cityArr) {
        this.cityArr = cityArr;
    }
}
