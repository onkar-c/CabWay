package com.example.core.CommonModels;

import com.google.gson.annotations.SerializedName;

public class VehicleTypeModel {

    private int id;
    @SerializedName("imageIcon")
    private String carImageUrl;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarImageUrl() {
        return carImageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
