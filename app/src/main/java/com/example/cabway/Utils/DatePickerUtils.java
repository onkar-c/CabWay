package com.example.cabway.Utils;

import android.app.DatePickerDialog;
import android.content.Context;

import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;

import java.util.Calendar;

public class DatePickerUtils {

    public static void startDatePicker(Context context, DatePickerCallBackInterface datePickerCallBackInterface) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(context,
                (datePicker, arg1, arg2, arg3) -> {
                    String selectedDate = arg3 + "/" + arg2 + 1 + "/" + arg1;
                    datePickerCallBackInterface.setDateFromDatePicker(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
}
