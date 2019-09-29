package com.example.cabway.Utils;

import android.app.Activity;

import com.example.cabway.ui.Interfaces.DateTimePickerCallBackInterface;

import java.util.Calendar;

public class DateTimePicker {

    Activity context;
    private DateTimePickerCallBackInterface dateTimePickerCallBackInterface;
    private String date;
    private Calendar mCalendar;

    private DateTimePickerCallBackInterface timePickerResult = new DateTimePickerCallBackInterface() {
        @Override
        public void setDateTimeFromDatePicker(String selectedDateTime) {
            dateTimePickerCallBackInterface.setDateTimeFromDatePicker(date + " " + selectedDateTime);
        }
    };
    private DateTimePickerCallBackInterface datePickerResult = new DateTimePickerCallBackInterface() {
        @Override
        public void setDateTimeFromDatePicker(String selectedDateTime) {
            date = selectedDateTime;
            DateTimeUtils.startTimePicker(context, timePickerResult, mCalendar);
        }
    };

    public DateTimePicker(DateTimePickerCallBackInterface dateTimePickerCallBackInterface, Activity context) {
        this.dateTimePickerCallBackInterface = dateTimePickerCallBackInterface;
        this.context = context;
        mCalendar = Calendar.getInstance();
    }

    public void showDateTimePicker(String existingDate) {
        mCalendar = (TextValidationUtils.isEmpty(existingDate)) ? Calendar.getInstance() : DateTimeUtils.toCalendar(existingDate);
        DateTimeUtils.startDatePicker(context, datePickerResult, mCalendar, false);
    }
}
