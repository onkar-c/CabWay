package com.example.cabway.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.core.restApi.HeroesApiExecutor;
import com.example.database.entities.Hero;
import com.example.database.models.UserModel;

import java.util.List;

public class HeroesRepository {

    private static HeroesRepository instance;

    public static HeroesRepository getInstance() {
        if (instance == null) {
            instance = new HeroesRepository();

        }
        return instance;
    }

    public void getHeroesFromServer(MutableLiveData<Boolean> response, Context context) {
        HeroesApiExecutor.getUsersFromServer(response, context);
    }

    public void getHeroesFromDb(Context context, MutableLiveData<List<Hero>> response) {
        new UserModel(context).getUsers(response);
    }
}
