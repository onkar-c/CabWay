package com.example.cabway.Utils;

import android.app.DatePickerDialog;
import android.content.Context;

import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;

import java.util.Calendar;

public class DatePickerUtils {

    public static void startDatePicker(Context context, DatePickerCallBackInterface datePickerCallBackInterface) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(context,
                (datePicker, year, arg2, day) -> {
                    String month = "" + ((arg2 + 1) < 10 ? "0" + (arg2 + 1) : (arg2 + 1));
                    String selectedDate = year + "-" + month + "-" + day;
                    datePickerCallBackInterface.setDateFromDatePicker(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void startDatePickerWithTodaysDate(Context context, DatePickerCallBackInterface datePickerCallBackInterface) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (datePicker, year, arg2, day) -> {
                    String month = "" + ((arg2 + 1) < 10 ? "0" + (arg2 + 1) : (arg2 + 1));
                    String dayFormat = "" + (day < 10 ? "0" + day : day);
                    String selectedDate = year + "-" + month + "-" + day;
                    datePickerCallBackInterface.setDateFromDatePicker(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


}
