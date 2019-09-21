package com.example.cabway.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.database.AppDatabase;
import com.example.database.entities.Hero;

public class UserWorker extends Worker {
    private AppDatabase appDatabase;

    public UserWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        appDatabase = AppDatabase.getInstance(context);

    }

    @NonNull
    @Override
    public Result doWork() {
        Hero hero = new Hero();
        hero.setId(1);
        hero.setData("abc");
        if (appDatabase.userDao().insertHero(hero) > 0) {
            return Result.success();
        }
        return Result.failure();
    }
}
