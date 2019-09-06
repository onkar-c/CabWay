package com.example.cabway.Utils;

import android.app.DatePickerDialog;
import android.content.Context;

import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;
import com.example.database.Utills.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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


    public static String getDateTimeToDisplay(Date date){
        return new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_DISPLAY, Locale.ENGLISH).format(date);
  }
    public static String convertDate(String inputDate){
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_REQUEST);
        SimpleDateFormat output = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_DISPLAY);
        Date d = null;
        try {
            d = sdf.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(d);
    }

    public static String getDateTimeForApiReq(String dateStr){
        String str = null;

        try {
            Date date = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_DISPLAY,Locale.ENGLISH).parse(dateStr);
            str = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_REQUEST,Locale.ENGLISH).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
        //return new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_REQUEST, Locale.ENGLISH).format(date);
    }
}
