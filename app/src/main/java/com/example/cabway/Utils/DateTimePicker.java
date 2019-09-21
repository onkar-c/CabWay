package com.example.cabway.Utils;

import android.app.Activity;

import com.example.cabway.ui.Interfaces.DateTimePickerCallBackInterface;

public class DateTimePicker {

    Activity context;
    private DateTimePickerCallBackInterface dateTimePickerCallBackInterface;
    private String date;

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
            DateTimeUtils.startTimePicker(context, timePickerResult);
        }
    };

    public DateTimePicker(DateTimePickerCallBackInterface dateTimePickerCallBackInterface, Activity context) {
        this.dateTimePickerCallBackInterface = dateTimePickerCallBackInterface;
        this.context = context;
    }

    public void showDateTimePicker() {
        DateTimeUtils.startDatePicker(context, datePickerResult);
    }
}
