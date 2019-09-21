package com.example.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.database.entities.Hero;

import java.util.List;

@Dao
public interface HeroesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllHeroes(List<Hero> order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertHero(Hero hero);

    @Query("SELECT * FROM Hero")
    LiveData<List<Hero>> getAll();
}

