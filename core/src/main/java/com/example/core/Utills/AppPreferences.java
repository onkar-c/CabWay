package com.example.core.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.core.CommonModels.VehicleTypeModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.CommonModels.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AppPreferences {
    private static AppPreferences instance;
    private final SharedPreferences sharedPreferences;
    private final String CLEARING_TAG = "Clearing Preference";

    public AppPreferences(Context context) {
        instance = this;
        sharedPreferences = context.getSharedPreferences(Constants.NAME, Context.MODE_PRIVATE);
    }

    public static AppPreferences getInstance() {
        return instance;
    }

    private void delete(String key) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        if (sharedPreferences.contains(key)) {
            sharedPreferencesEditor.remove(key).apply();
        }
    }

    private void savePref(String key, Object value) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        if (value instanceof Boolean) {
            sharedPreferencesEditor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            sharedPreferencesEditor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            sharedPreferencesEditor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            sharedPreferencesEditor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            sharedPreferencesEditor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            sharedPreferencesEditor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }

        sharedPreferencesEditor.apply();
    }


    private <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

//    public void clearAll() {
//        sharedPreferencesEditor.clear();
//    }

    public void setIsLogin(boolean isLogin) {
        savePref(Constants.IS_LOGIN, isLogin);
    }

    public boolean isLogin() {
        return getPref(Constants.IS_LOGIN, false);
    }

    public UserModel getUserDetails() {
        String userDetails = getPref(Constants.USER_DETAILS, "");
        if (userDetails != null && userDetails.length() > 0)
            return new Gson().fromJson(userDetails, UserModel.class);
        else
            return null;
    }

    public void setUserDetails(UserModel userModel) {
        savePref(Constants.USER_DETAILS, new Gson().toJson(userModel));
    }

    public void setCabWayEmail(String email) {
        savePref(Constants.CAB_WAY_EMAIL, email);
    }

    public String getCabWayEmail() {
        return getPref(Constants.CAB_WAY_EMAIL, "");
    }

    public void setCabWayNumber(String number) {
        savePref(Constants.CAB_WAY_NUMBER, number);
    }


    public String getDataVersion() {
        return getPref(Constants.DATA_VERSION, "0");
    }

    public void setDataVersion(String version) {
        savePref(Constants.DATA_VERSION, version);
    }

    public String getCabWayNumber() {
        return getPref(Constants.CAB_WAY_NUMBER, "");
    }

    public String getAuthKey() {
        return getPref(Constants.AUTH_KEY, "");
    }

    public void setAuthKey(String authKey) {
        savePref(Constants.AUTH_KEY, authKey);
    }

    public void setStateList(List<StateModel> data) {
        savePref(Constants.STATE_DATA, new Gson().toJson(data));
    }

    public ArrayList<StateModel> getStateList() {
        ArrayList<StateModel> stateList =  new Gson().fromJson(getPref(Constants.STATE_DATA, ""), new TypeToken<ArrayList<StateModel>>() {
        }.getType());
        return (stateList != null)? stateList : new ArrayList<StateModel>();
    }

    public void setCityList(List<CityModel> data) {
        savePref(Constants.CITY_DATA, new Gson().toJson(data));
    }

    public ArrayList<CityModel> getCityList() {
        return new Gson().fromJson(getPref(Constants.CITY_DATA, ""), new TypeToken<ArrayList<CityModel>>() {
        }.getType());
    }

    public void setVehicleList(List<VehicleTypeModel> data) {
        savePref(Constants.VEHICLE_TYPE_DATA,  new Gson().toJson(data));
    }

    public ArrayList<VehicleTypeModel> getVehicleTypeList() {
        return new Gson().fromJson(getPref(Constants.VEHICLE_TYPE_DATA, ""), new TypeToken<ArrayList<VehicleTypeModel>>() {
        }.getType());
    }


    public void clearPreferencesForLogout() {
        setIsLogin(false);
        clearAuthKey();
        clearUserDetails();
    }

    private void clearUserDetails() {
        Log.e(CLEARING_TAG, "Clearing User Details====");
        delete(Constants.USER_DETAILS);
    }

    private void clearAuthKey() {
        Log.e(CLEARING_TAG, "Clearing Auth Key====");
        delete(Constants.AUTH_KEY);
    }


    private class Constants {
        private static final String NAME = "CabWay";
        private static final String USER_DETAILS = "user_details";
        private static final String IS_LOGIN = "is_login";
        private static final String AUTH_KEY = "auth_key";
        private static final String STATE_DATA = "state_data";
        private static final String CITY_DATA = "city_data";
        private static final String VEHICLE_TYPE_DATA = "vehicle_type_data";
        private static final String CAB_WAY_EMAIL = "cab_way_email";
        private static final String CAB_WAY_NUMBER = "cab_way_number";
        private static final String DATA_VERSION = "data_version";
    }
}