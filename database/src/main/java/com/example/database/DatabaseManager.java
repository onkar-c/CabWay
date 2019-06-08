package com.example.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseManager {
    private static final String DB_NAME = "db_task";
    private static AppDatabase instance;

    public static AppDatabase getDatabaseInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
