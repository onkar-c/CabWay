package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.CarTypeSpinnerUtils;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.DateTimePicker;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.ui.adapter.CarTypeSpinnerAdapter;
import com.example.cabway.viewModels.RidesViewModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.VehicleTypeModel;
import com.example.core.requestModels.CreateRideRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.responseModel.RideResponseModel;
import com.example.database.Utills.AppConstants;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;

public class CreateRideActivity extends BaseActivity implements CarTypeSpinnerAdapter.TypeSelectedCallback, DateTimePicker.ResultCallback {

    public final int startLocation = 1;
    public final int endLocation = 2;
    @BindView(R.id.et_start_loc)
    EditText fromLocET;
    @BindView(R.id.et_drop_loc)
    EditText toLocET;
    @BindView(R.id.sp_vehicle_type)
    AppCompatSpinner vehicleTypeSP;
    @BindView(R.id.et_pickup_date_time)
    EditText pickupDateTimeET;
    @BindView(R.id.il_pickup_date_time)
    TextInputLayout pickupDateTimeTIL;
    @BindView(R.id.et_drop_off_date_time)
    EditText dropOffDateTimeET;
    @BindView(R.id.il_drop_off_date_time)
    TextInputLayout dropOffDateTimeTIL;
    @BindView(R.id.et_cost_of_trip)
    EditText costOfTripET;
    @BindView(R.id.et_distance)
    EditText distanceET;
    @BindView(R.id.btn_book_ride)
    Button bookRideBtn;
    @BindView(R.id.rg_ride_type)
    RadioGroup rideTypeRg;
    @BindView(R.id.rb_one_way)
    RadioButton oneWay;
    @BindView(R.id.rb_two_way)
    RadioButton twoWay;
    RidesViewModel ridesViewModel;
    CityModel fromCity, toCity;
    RideResponseModel ride = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);
        ButterKnife.bind(this);
        setUpActionBar();
        ridesViewModel = ViewModelProviders.of(this).get(RidesViewModel.class);
        ridesViewModel.init();
        ride = (RideResponseModel) getIntent().getSerializableExtra(IntentConstants.RIDE);
        setCreateRideObserver();
        setUpUi();
        if (ride != null)
            setRideToDisplay();
    }

    private void setRideToDisplay() {

        fromCity = ride.getFromCity();
        toCity = ride.getToCity();
        fromLocET.setText(ride.getFromCity().getName());
        toLocET.setText(ride.getToCity().getName());
        vehicleTypeSP.setSelection(CarTypeSpinnerUtils.getVehicleTypePosition(ride.getCarType()));
        pickupDateTimeET.setText(DatePickerUtils.convertDate(ride.getPickupTime()));
        dropOffDateTimeET.setText(DatePickerUtils.convertDate(ride.getDropTime()));
        costOfTripET.setText(String.format("%s", ride.getCost().toString()));
        distanceET.setText(String.format("%s", ride.getDistance().toString()));
        bookRideBtn.setText(getString(R.string.update));
        if (ride.getRideType().equals(AppConstants.ONE_WAY))
            oneWay.setChecked(true);
        else
            twoWay.setChecked(true);
    }


    private void setCreateRideObserver() {
        ridesViewModel.getCreateRideMld().observe(this, (JsonResponse createRideResponse) -> {
            removeProgressDialog();
            if (Objects.requireNonNull(createRideResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(CreateRideActivity.this, createRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
                if(ride != null)
                    sendResultData(createRideResponse.getRide());
                else
                    onBackPressed();
            } else {
                Toast.makeText(CreateRideActivity.this, createRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendResultData(RideResponseModel pRide){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(IntentConstants.RIDE, pRide);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void setUpUi() {
        vehicleTypeSP.setAdapter(CarTypeSpinnerUtils.setSpinnerAdapter(this, vehicleTypeSP));
    }


    @OnClick(R.id.et_pickup_date_time)
    public void selectDate() {
        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setDateResultCallback(CreateRideActivity.this);
        dateTimePicker.showDialog(CreateRideActivity.this, 1000, true);
    }

    @OnClick(R.id.et_drop_off_date_time)
    public void selectTime() {
        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setDateResultCallback(CreateRideActivity.this);
        dateTimePicker.showDialog(CreateRideActivity.this, 1000, false);
    }

    @OnClick(R.id.et_start_loc)
    public void onClickOfFromLocEditText() {
        Intent nextActivity = new Intent(this, PreferredCityActivity.class);
        nextActivity.putExtra(IntentConstants.IS_FROM_CREATE_RIDE, true);
        startActivityForResult(nextActivity, startLocation);
    }

    @OnClick(R.id.et_drop_loc)
    public void onClickOfToLocEditText() {
        Intent nextActivity = new Intent(this, PreferredCityActivity.class);
        nextActivity.putExtra(IntentConstants.IS_FROM_CREATE_RIDE, true);
        startActivityForResult(nextActivity, endLocation);

    }

    @OnClick(R.id.btn_book_ride)
    public void bookRide() {
        if (checkNetworkAvailableWithoutError() && validate()) {
            showProgressDialog(AppConstants.PLEASE_WAIT, false);
            ridesViewModel.getRidesRepository().createRide(ridesViewModel.getCreateRideMld(), getRideDetails(), (ride != null));
        }

    }

    @Override
    public void sendTypeOnSelection(VehicleTypeModel data) {

    }

    private boolean validate() {
        if (vehicleTypeSP.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.vehicle_type, this);
            return false;
        } else if (pickupDateTimeET.getText().toString().isEmpty()) {
            return false;
        } else if (dropOffDateTimeET.getText().toString().isEmpty()) {
            showMandatoryError(R.string.time_of_journey_hint, this);
            return false;
        } else if (distanceET.getText().toString().trim().isEmpty()) {
            showMandatoryError(R.string.distance_hint, this);
            return false;
        } else if (costOfTripET.getText().toString().trim().isEmpty()) {
            showMandatoryError(R.string.cost_of_trip_hint, this);
            return false;
        } else if (dropOffDateTimeET.getText().toString().compareTo(pickupDateTimeET.getText().toString()) < 0) {
            Toast.makeText(this, getString(R.string.date_validation_create_ride), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private CreateRideRequestModel getRideDetails() {
        CreateRideRequestModel rideRequestModel = new CreateRideRequestModel();
        if (ride != null) {
            rideRequestModel.setRideId(ride.getRideId());
        }
        rideRequestModel.setCarType(((VehicleTypeModel) vehicleTypeSP.getSelectedItem()).getType());
        rideRequestModel.setAgencyMobileNo(appPreferences.getUserDetails().mobileNo);
        rideRequestModel.setCost(Integer.parseInt(costOfTripET.getText().toString().trim()));
        rideRequestModel.setDistance(Integer.parseInt(distanceET.getText().toString()));
        rideRequestModel.setFromCity(fromCity);
        rideRequestModel.setToCity(toCity);
        rideRequestModel.setRideType((rideTypeRg.getCheckedRadioButtonId() == R.id.rb_one_way) ? AppConstants.ONE_WAY : AppConstants.TWO_WAY);
        rideRequestModel.setPickupTime(DatePickerUtils.getDateTimeForApiReq(pickupDateTimeET.getText().toString()));
        rideRequestModel.setDropTime(DatePickerUtils.getDateTimeForApiReq(dropOffDateTimeET.getText().toString()));

        return rideRequestModel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            CityModel cityModel = (CityModel) Objects.requireNonNull(data).getSerializableExtra(IntentConstants.PREFERRED_CITY);
            if (requestCode == startLocation) {
                fromLocET.setText(cityModel.getName());
                fromCity = cityModel;
            } else {
                toLocET.setText(cityModel.getName());
                toCity = cityModel;
            }
        }


    }

    @Override
    public void onResult(@Nullable Object o, boolean isPickup) {
        Log.e("result", o + "");
        if (isPickup) {
            pickupDateTimeET.setText(DatePickerUtils.getDateTimeToDisplay((Date) o));
        } else {
            dropOffDateTimeET.setText(DatePickerUtils.getDateTimeToDisplay((Date) o));
        }
    }

}
