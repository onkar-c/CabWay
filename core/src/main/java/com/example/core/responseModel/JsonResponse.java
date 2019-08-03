package com.example.core.responseModel;

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

    public List<HeroesModel> getHeroesList() {
        return heroesList;
    }

//    public void setHeroesList(List<HeroesModel> heroesList) {
//        this.heroesList = heroesList;
//    }

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
}
