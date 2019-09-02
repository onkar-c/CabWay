package com.example.cabway.Utils;

import android.app.DatePickerDialog;
import android.content.Context;

import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerUtils {

    public static void startDatePicker(Context context, DatePickerCallBackInterface datePickerCallBackInterface) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(context,
                (datePicker, year, arg2, day) -> {
                    String month = "" + ((arg2 + 1) < 10 ? "0" + (arg2 + 1) : (arg2 + 1));
                    String dayFormat = "" + (day < 10 ? "0" + day : day);
                    String selectedDate = year + "-" + month + "-" + dayFormat;
                    datePickerCallBackInterface.setDateFromDatePicker(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void startDatePickerWithTodaysDate(Context context, DatePickerCallBackInterface datePickerCallBackInterface) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (datePicker, year, arg2, day) -> {
                    String month = "" + ((arg2 + 1) < 10 ? "0" + (arg2 + 1) : (arg2 + 1));
                    String dayFormat = "" + (day < 10 ? "0" + day : day);
                    String selectedDate = year + "-" + month + "-" + dayFormat;
                    datePickerCallBackInterface.setDateFromDatePicker(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public static String convertDate(String inputDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(d);
    }

}
