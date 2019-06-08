package com.example.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.database.entities.Hero;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface HeroesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllHeroes(List<Hero> order);

    @Query("SELECT * FROM Hero")
    Single<List<Hero>> getAll();
}

