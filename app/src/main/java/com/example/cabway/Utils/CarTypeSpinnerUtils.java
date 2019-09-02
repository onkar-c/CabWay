package com.example.cabway.Utils;

import android.app.Activity;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.cabway.ui.adapter.CarTypeSpinnerAdapter;

import java.util.ArrayList;

public class CarTypeSpinnerUtils {

    public static CarTypeSpinnerAdapter setSpinnerAdapter(Activity context, AppCompatSpinner spinner) {
        return new CarTypeSpinnerAdapter(context, spinner, new ArrayList<>());

    }
}
