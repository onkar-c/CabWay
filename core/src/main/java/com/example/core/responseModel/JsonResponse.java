package com.example.core.responseModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JsonResponse implements Serializable{

    @SerializedName("heroes")
    private List<HeroesModel> heroesList;

    public List<HeroesModel> getHeroesList() {
        return heroesList;
    }
}
