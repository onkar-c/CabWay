package com.example.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.database.dao.HeroesDao;
import com.example.database.entities.Hero;

@Database(entities = {Hero.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase = null;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DatabaseConstants.DATABASE_NAME).build();
        }
        return appDatabase;
    }

    public abstract HeroesDao userDao();

}
