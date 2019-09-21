package com.example.cabway.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cabway.repositories.HeroesRepository;
import com.example.database.entities.Hero;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private HeroesRepository myRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        myRepository = HeroesRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Hero>> getHeroes() {
        return myRepository.getHeroesFromDb();
    }
}
