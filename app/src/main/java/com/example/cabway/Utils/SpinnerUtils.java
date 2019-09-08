package com.example.cabway.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.Utills.AppPreferences;
import com.example.database.Utills.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpinnerUtils {

    private static List<StateModel> getStateList() {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        List<StateModel> stateList = new ArrayList<>();
        StateModel stateModel = new StateModel(-1, "Select State");
        stateList.add(0, stateModel);
        stateList.addAll(tempList);
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
        CityModel cityModel = tempList.get(tempList.indexOf(new CityModel(pCityCode)));
        if(cityModel != null){
            return getStateData(cityModel.getStateCode());
        }
        return null;
    }


    public static int getCityPosition(int cityCode, int stateCode) {
        List<CityModel> tempList = getCityList(stateCode);
        int pos = tempList.indexOf(new CityModel(cityCode));
        return (pos >= 0) ? pos : 0;
    }

    private static StateModel getStateData(int stateCode) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        return tempList.get(tempList.indexOf(new StateModel(stateCode)));
    }

    public static int getStatePosition(int stateCode) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        int pos = tempList.indexOf(new StateModel(stateCode));
        return (pos >= 0) ? pos + 1 : 0;
    }

    public static int getStatePositionByName(String stateName) {
        List<StateModel> tempList = AppPreferences.getInstance().getStateList();
        int pos = tempList.indexOf(new StateModel(stateName));
        return (pos >= 0) ? pos + 1: 0;
    }

    public static CitySpinnerAdapter setSpinnerAdapter(Activity context, String type, int stateCode, AppCompatSpinner spinner) {
        if (type.equalsIgnoreCase(AppConstants.STATE)) {
            return new CitySpinnerAdapter<>(context, spinner, getStateList());
        } else if (type.equalsIgnoreCase(AppConstants.CITY)) {
            return new CitySpinnerAdapter<>(context, spinner, getCityList(stateCode));
        }
        return null;
    }

    public static boolean stateCityMatches(int stateId, int cityId) {
        List<CityModel> tempList = AppPreferences.getInstance().getCityList();
        int cityPosition = tempList.indexOf(new CityModel(cityId));
        if(cityPosition >= 0)
            return tempList.get(cityPosition).getState().getId() == stateId;
        return false;
    }

    public static void hideSoftKeyboard(View v) {
        InputMethodManager in = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(in).hideSoftInputFromWindow(v.getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
