package com.example.cabway.Utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class DateTimePicker {
    @NonNull
    private final Calendar calendar = Calendar.getInstance();
    @Nullable
    private DatePickerDialog datePickerDialog;
    @Nullable
    private TimePickerDialog timePickerDialog;
    @Nullable
    private ResultCallback<Date> dateResultCallback;
    private boolean isPickup;
    private long minimumTime;

    public void showDialog(@NonNull Context context, long time,boolean isPickup) {
        calendar.setTimeInMillis(System.currentTimeMillis() - 1000);
        this.isPickup=isPickup;
        this.minimumTime=time;
        closeDialogs();
        showDatePicker(context);
    }

    @Nullable
    public ResultCallback<Date> getDateResultCallback() {
        return dateResultCallback;
    }

    public void setDateResultCallback(@Nullable ResultCallback<Date> dateResultCallback) {
        this.dateResultCallback = dateResultCallback;
    }

    public long getTime() {
        return calendar.getTimeInMillis();
    }

    private void closeDialogs() {
        if (datePickerDialog != null) {
            datePickerDialog.dismiss();
            datePickerDialog = null;
        }
        if (timePickerDialog != null) {
            timePickerDialog.dismiss();
            timePickerDialog = null;
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            timePicker(view.getContext());
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            if (dateResultCallback != null) {
                dateResultCallback.onResult(calendar.getTime(),isPickup);
            }
        }
    };

    private void showDatePicker(@NonNull Context context) {
        datePickerDialog = new DatePickerDialog(context,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
       /* TODO:if(minimumTime!=0){
            datePickerDialog.getDatePicker().setMinDate(minimumTime);
        }else*/
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void timePicker(@NonNull Context context) {
        timePickerDialog = new TimePickerDialog(context,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false);
        timePickerDialog.show();
    }

    public void release() {
        closeDialogs();
        dateResultCallback = null;
    }

    public interface ResultCallback<Result> { void onResult(@Nullable Result result,boolean isPickup); }

}
