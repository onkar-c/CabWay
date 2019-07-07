package com.example.core.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AppPreferences {
    private static AppPreferences instance;
    private final SharedPreferences sharedPreferences;

    public AppPreferences(Context context) {
        instance = this;
        sharedPreferences = context.getSharedPreferences(Constants.NAME, Context.MODE_PRIVATE);
    }

    public static AppPreferences getInstance() {
        if (instance == null) {
            throw new NullPointerException("AppPreferences was not initialized!");
        }
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

    public void saveLoginType(String type) {
        savePref(Constants.LOGIN_TYPE, type);
    }

    public String getLoginType() {
        return getPref(Constants.LOGIN_TYPE, "");
    }

    public ArrayList<String> getNewUpadatesIds() {
        return new Gson().fromJson(getPref(Constants.NEW_UPDATES_IDS, ""), new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    public void setNewUpadatesIds(ArrayList<String> noteIdsList) {
        if (noteIdsList != null && noteIdsList.size() > 0)
            savePref(Constants.NEW_UPDATES_IDS, new Gson().toJson(noteIdsList));
        else
            savePref(Constants.NEW_UPDATES_IDS, "");

    }

    public void clearUserData() {
        Log.e("Clearing user Data====", "Clearing user Data====");
        delete(Constants.UNSYNCED_WORKSPACE_INVITES_LIST);
    }


    private class Constants {
        private static final String NAME = "CabWay";
        private static final String USER_UUID = "user_uuid";
        private static final String UNSYNCED_WORKSPACE_INVITES_LIST = "unsynced_workspace_invites_list";
        private static final String NEW_UPDATES_IDS = "new_updates_ids";
        private static final String LOGIN_TYPE = "login_type";

    }
}