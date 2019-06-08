package com.example.core.Utills;

import com.example.core.responseModel.HeroesModel;
import com.example.database.entities.Hero;

import java.util.ArrayList;
import java.util.List;

public class HeroUtils {

    private static Hero convertHeroModelToHero(HeroesModel heroesModel) {
        Hero hero = new Hero();
        hero.setData(heroesModel.getName());
        return hero;
    }

    public static List<Hero> convertHeroModelLitsToHeroList(List<HeroesModel> heroesModelList) {
        List<Hero> heroList = new ArrayList<>();
        for (HeroesModel heroesModel : heroesModelList) {
            heroList.add(convertHeroModelToHero(heroesModel));
        }
        return heroList;
    }
}
