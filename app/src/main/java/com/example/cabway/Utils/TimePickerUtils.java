package com.example.cabway.Utils;

import android.app.TimePickerDialog;
import android.content.Context;

import com.example.cabway.ui.Interfaces.TimePickerCallBackInterface;
import com.example.database.Utills.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimePickerUtils {

    public static void openTimePicker(Context context, TimePickerCallBackInterface timePickerCallBackInterface){
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        new TimePickerDialog(context,(view, hourOfDay, minute) -> {
            String timeStr=get12HoursTime(hourOfDay,minute);
            timePickerCallBackInterface.setTimeFromTimePicker(timeStr);
        },mHour,mMinute,false).show();
    }

    private static String get12HoursTime(int hour,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);

        return new SimpleDateFormat(AppConstants.TIME_FORMAT, Locale.ENGLISH).format(calendar.getTime());
    }
}
