package com.example.cabway.Utils;

import android.content.Context;

import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.Utills.AppPreferences;
import com.example.database.Utills.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class SpinnerUtils {

    private static List<StateModel> getStateList() {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        List<StateModel> stateList = new ArrayList<>();
        StateModel stateModel = new StateModel("-1", "Select State");
        stateList.add(0, stateModel);
        for (int i = 1; i < stateList.size(); i++) {
            stateList.add(i, tempList.get(i - 1));
        }
        return stateList;
    }

    private static List<CityModel> getCityList(String pStateCode) {
        List<CityModel> cityList = new ArrayList<>();
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();

        CityModel cityModel = new CityModel("-1", "Select Sity", "-1");
        cityList.add(0, cityModel);
        int k = 1;
        for (CityModel city : tempList) {
            if (city.getStateCode().equals(pStateCode)) {
                cityList.add(k, city);
                k++;
            }
        }
        return cityList;
    }

    public static StateModel getStateOfCity(String pCityCode) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        for (CityModel city : tempList) {
            if (city.getCode().equals(pCityCode)) {
                return getStateData(city.getStateCode());
            }
        }
        return null;
    }

    public static CityModel getCity(String cityCode) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        for (CityModel city : tempList) {
            if (city.getCode().equals(cityCode)) {
                return city;
            }
        }
        return null;
    }

    public static int getCityPosition(String cityCode) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getCode().equals(cityCode)) {
                return i + 1;//spinner list contains extra select element
            }
        }
        return 0;
    }

    private static StateModel getStateData(String stateCode) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        for (StateModel state : tempList) {
            if (state.getId().equals(stateCode))
                return state;
        }
        return null;
    }

    public static StateModel getStateData(long position) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        return tempList.get((int) position - 1);
    }

    public static CityModel getCityData(long position) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        return tempList.get((int) position - 1);
    }

    public static int getStatePosition(String stateCode) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        for (int j = 0; j < tempList.size(); j++) {
            if (tempList.get(j).getId().equals(stateCode))
                return j + 1;
        }
        return 0;
    }

    public static CitySpinnerAdapter setSpinnerAdapter(Context context, String type, String stateCode) {
        if (type.equalsIgnoreCase(AppConstants.STATE)) {
            return new CitySpinnerAdapter<>(context, getStateList());
        } else if (type.equalsIgnoreCase(AppConstants.CITY)) {
            return new CitySpinnerAdapter<>(context, getCityList(stateCode));
        }
        return null;
    }

}
