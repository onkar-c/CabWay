package com.example.cabway.Utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;

import com.example.cabway.ui.Interfaces.DateTimePickerCallBackInterface;
import com.example.database.Utills.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateTimeUtils {

    public static void startDatePicker(Context context, DateTimePickerCallBackInterface dateTimePickerCallBackInterface, Calendar calendar, boolean hasMaxDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (datePicker, year, arg2, day) -> {
                    String month = "" + ((arg2 + 1) < 10 ? "0" + (arg2 + 1) : (arg2 + 1));
                    String dayFormat = "" + (day < 10 ? "0" + day : day);
                    String selectedDate = dayFormat + "-" + month + "-" + year;
                    dateTimePickerCallBackInterface.setDateTimeFromDatePicker(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (hasMaxDate)
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        else
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    static void startTimePicker(Context context, DateTimePickerCallBackInterface timePickerCallBackInterface, Calendar calendar) {
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            Log.i("time", hourOfDay + " " + minute);
            timePickerCallBackInterface.setDateTimeFromDatePicker(get12HoursTime(hourOfDay, minute));
        }, mHour, mMinute, false).show();
    }

    private static String get12HoursTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return new SimpleDateFormat(AppConstants.TIME_FORMAT, Locale.ENGLISH).format(calendar.getTime());
    }

    public static String convertDate(String inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_REQUEST, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_DISPLAY, Locale.ENGLISH);
        Date d = null;
        try {
            d = sdf.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(d);
    }

    public static String getDateTimeForApiReq(String dateStr) {
        String str = null;

        try {
            Date date = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_DISPLAY, Locale.ENGLISH).parse(dateStr);
            str = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_REQUEST, Locale.ENGLISH).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
        //return new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_REQUEST, Locale.ENGLISH).format(date);
    }

    public static boolean before(String dropOff, String pickup) {
        SimpleDateFormat format = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_DISPLAY, Locale.ENGLISH);
        try {
            Date dropOffDateTime = format.parse(dropOff);
            Date pickupDateTime = format.parse(pickup);
            if (dropOffDateTime.before(pickupDateTime)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }


    static Calendar toCalendar(String strDate){
        Date date = null;
        try {
            date = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOR_DISPLAY, Locale.ENGLISH).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
