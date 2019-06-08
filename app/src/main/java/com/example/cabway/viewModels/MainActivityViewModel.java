package com.example.cabway.viewModels;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.database.entities.Hero;
import com.example.cabway.repositories.HeroesRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel{

    private MutableLiveData<List<Hero>> response ;
    private MutableLiveData<Boolean> responseRecived ;
    private HeroesRepository myRepository;

    public void init(){
        if(response != null)
            return;
        myRepository = HeroesRepository.getInstance();
        response  = new MutableLiveData<>();
        responseRecived  = new MutableLiveData<>();
    }

    public LiveData<List<Hero>> getResponse(){
        return response;
    }

    public MutableLiveData<Boolean> getResponseRecived() {
        return responseRecived;
    }

    public void makeCall(Activity context){
       myRepository.getHeroesFromServer(responseRecived,context);
    }

    public void getHeroes(Context context){
        myRepository.getHeroesFromDb(context, response);
    }
}
