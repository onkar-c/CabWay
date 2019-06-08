package com.example.database.models;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.database.AppDatabase;
import com.example.database.DatabaseManager;
import com.example.database.entities.Hero;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserModel {

    private static AppDatabase appDatabase;

    public UserModel(Context context) {
        appDatabase = DatabaseManager.getDatabaseInstance(context);
    }

    @SuppressLint("CheckResult")
    public void insertUsers(List<Hero> heroList, final MutableLiveData<Boolean> inserted) {
        getInsertUserComplitable(heroList)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    if (inserted != null)
                        inserted.setValue(true);
                });
    }

    private Completable getInsertUserComplitable(List<Hero> heroList) {
        return Completable.fromAction(() -> appDatabase.userDao().insertAllHeroes(heroList));
    }


    @SuppressLint("CheckResult")
    public void getUsers(MutableLiveData<List<Hero>> response) {

        appDatabase.userDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Hero>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Hero> heroList) {
                        response.setValue(heroList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
