package com.example.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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

