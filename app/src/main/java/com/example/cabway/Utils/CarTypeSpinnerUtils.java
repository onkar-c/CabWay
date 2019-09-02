package com.example.cabway.Utils;

import android.app.Activity;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.cabway.ui.adapter.CarTypeSpinnerAdapter;
import com.example.core.CommonModels.VehicleTypeModel;
import com.example.core.Utills.AppPreferences;

import java.util.ArrayList;
import java.util.List;

public class CarTypeSpinnerUtils {

    public static CarTypeSpinnerAdapter setSpinnerAdapter(Activity context, AppCompatSpinner spinner) {
        return new CarTypeSpinnerAdapter(context, spinner, new ArrayList<>());
    }

    public static int getVehicleTypePosition(String vehicleType){
        List<VehicleTypeModel> vehicleTypeList= AppPreferences.getInstance().getVehicleTypeList();
        for(int i=0 ;i<vehicleTypeList.size();i++){
            if(vehicleTypeList.get(i).getType().equalsIgnoreCase(vehicleType))
                return i+1;
        }
        return 0;
    }
}
