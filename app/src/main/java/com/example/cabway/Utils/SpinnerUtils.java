package com.example.cabway.Utils;

import android.app.Activity;

import androidx.appcompat.widget.AppCompatSpinner;

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
        StateModel stateModel = new StateModel(-1, "Select State");
        stateList.add(0, stateModel);
        for (int i = 1; i <= tempList.size(); i++) {
            stateList.add(i, tempList.get(i - 1));
        }
        return stateList;
    }

    private static List<CityModel> getCityList(int pStateCode) {
        List<CityModel> cityList = new ArrayList<>();
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();

        CityModel cityModel = new CityModel(-1, "Select City");
        cityList.add(0, cityModel);
        int k = 1;
        for (CityModel city : tempList) {
            if (city.getStateCode() == pStateCode) {
                cityList.add(k, city);
                k++;
            }
        }
        return cityList;
    }

    public static StateModel getStateOfCity(int pCityCode) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        for (CityModel city : tempList) {
            if (city.getCityId()== pCityCode ) {
                return getStateData(city.getStateCode());
            }
        }
        return null;
    }

    public static StateModel getStateOfCityByCityName(String pCityName) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        for (CityModel city : tempList) {
            if (city.getName().equalsIgnoreCase(pCityName)){
                return getStateData(city.getStateCode());
            }
        }
        return null;
    }

    public static CityModel getCity(int cityCode) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        for (CityModel city : tempList) {
            if (city.getCityId() == cityCode ) {
                return city;
            }
        }
        return null;
    }

    public static int getCityPosition(int cityCode,int stateCode) {
        List<CityModel> tempList = getCityList(stateCode);
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getCityId() == cityCode) {
                return i ;//spinner list contains extra select element
            }
        }
        return 0;
    }

    public static int getCityPositionByName(String cityName) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getName().equalsIgnoreCase(cityName)) {
                return i ;//spinner list contains extra select element
            }
        }
        return 0;
    }

    private static StateModel getStateData(int stateCode) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        for (StateModel state : tempList) {
            if (state.getId()== stateCode)
                return state;
        }
        return null;
    }

    public static StateModel getStateDataByPosition(long position) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        return tempList.get((int) position - 1);
    }

    public static CityModel getCityData(long position,int stateCode) {
        List<CityModel> tempList = getCityList(stateCode);
        return tempList.get((int) position - 1);
    }

    public static int getStatePosition(int stateCode) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        for (int j = 0; j < tempList.size(); j++) {
            if (tempList.get(j).getId() == stateCode)
                return j + 1;
        }
        return 0;
    }

    public static int getStatePositionByName(String stateName) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        for (int j = 0; j < tempList.size(); j++) {
            if (tempList.get(j).getName().equalsIgnoreCase(stateName))
                return j + 1;
        }
        return 0;
    }

    public static CitySpinnerAdapter setSpinnerAdapter(Activity context, String type, int stateCode, AppCompatSpinner spinner) {
        if (type.equalsIgnoreCase(AppConstants.STATE)) {
            return new CitySpinnerAdapter<>(context,spinner, getStateList());
        } else if (type.equalsIgnoreCase(AppConstants.CITY)) {
            return new CitySpinnerAdapter<>(context,spinner, getCityList(stateCode));
        }
        return null;
    }

}
