package com.example.cabway.ui.activities;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.example.cabway.R;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.TimePickerUtils;
import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;
import com.example.cabway.ui.Interfaces.TimePickerCallBackInterface;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateRideActivity extends BaseActivity implements DatePickerCallBackInterface, TimePickerCallBackInterface {

    @BindView(R.id.et_home_loc)
    EditText fromLocET;

    @BindView(R.id.et_office_loc)
    EditText toLocET;

    @BindView(R.id.et_car_type)
    EditText carTypeET;

    @BindView(R.id.et_date_of_journey)
    EditText dateOfJourneyET;

    @BindView(R.id.il_date_of_journey)
    TextInputLayout dateOfJourneyTIL;

    @BindView(R.id.et_time_of_journey)
    EditText timeOfJourneyET;

    @BindView(R.id.il_time_of_journey)
    TextInputLayout timeOfJourneyTIL;

    @BindView(R.id.et_agency_mob_no)
    EditText agencyMobNoET;

    @BindView(R.id.et_trip_mode)
    EditText tripModeET;

    @BindView(R.id.et_cost_of_trip)
    EditText costOfTripET;

    @BindView(R.id.btn_book_ride)
    Button bookRideBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);
        setUpActionBar();
    }


    @OnClick(R.id.il_date_of_journey)
    public void selectDate() {
        DatePickerUtils.startDatePicker(CreateRideActivity.this, CreateRideActivity.this);
    }

    @OnClick(R.id.il_time_of_journey)
    public void selectTime() {
        TimePickerUtils.openTimePicker(CreateRideActivity.this, CreateRideActivity.this);
    }

    @OnClick(R.id.et_home_loc)
    public void onClickOfFromLocEditText() {

    }

    @OnClick(R.id.et_office_loc)
    public void onClickOfToLocEditText() {

    }

    @OnClick(R.id.btn_book_ride)
    public void bookRide() {

    }

    @Override
    public void setDateFromDatePicker(String selectedDate) {
        dateOfJourneyET.setText(selectedDate);
    }

    @Override
    public void setTimeFromTimePicker(String selectedTime) {
        timeOfJourneyET.setText(selectedTime);
    }

}
