package com.example.cabway.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.database.AppDatabase;
import com.example.database.entities.Hero;

import java.util.List;

public class HeroesRepository {

    private static HeroesRepository instance;
    private static AppDatabase appDatabase;

    public static HeroesRepository getInstance(Context context) {
        if (instance == null) {
            instance = new HeroesRepository();
        }
        appDatabase = AppDatabase.getInstance(context);
        return instance;
    }

    public LiveData<List<Hero>> getHeroesFromDb() {
        return appDatabase.userDao().getAll();
    }

    public LiveData<Long> insertSingleHero(Hero hero) {
        MutableLiveData<Long> result = new MutableLiveData<>();
        result.setValue(appDatabase.userDao().insertHero(hero));
        return result;
    }
}
