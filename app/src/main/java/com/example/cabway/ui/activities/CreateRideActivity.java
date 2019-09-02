package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.CarTypeSpinnerUtils;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.TimePickerUtils;
import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;
import com.example.cabway.ui.Interfaces.TimePickerCallBackInterface;
import com.example.cabway.ui.adapter.CarTypeSpinnerAdapter;
import com.example.cabway.viewModels.RidesViewModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.VehicleTypeModel;
import com.example.core.requestModels.CreateRideRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.example.database.Utills.AppConstants;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;

public class CreateRideActivity extends BaseActivity implements DatePickerCallBackInterface, TimePickerCallBackInterface, CarTypeSpinnerAdapter.TypeSelectedCallback {

    @BindView(R.id.et_start_loc)
    EditText fromLocET;

    @BindView(R.id.et_drop_loc)
    EditText toLocET;

    @BindView(R.id.sp_vehicle_type)
    AppCompatSpinner vehicleTypeSP;

    @BindView(R.id.et_date_of_journey)
    EditText dateOfJourneyET;

    @BindView(R.id.il_date_of_journey)
    TextInputLayout dateOfJourneyTIL;

    @BindView(R.id.et_time_of_journey)
    EditText timeOfJourneyET;

    @BindView(R.id.il_time_of_journey)
    TextInputLayout timeOfJourneyTIL;

    @BindView(R.id.et_cost_of_trip)
    EditText costOfTripET;

    @BindView(R.id.et_distance)
    EditText distanceET;

    @BindView(R.id.btn_book_ride)
    Button bookRideBtn;

    @BindView(R.id.rg_ride_type)
    RadioGroup rideTypeRg;

    RidesViewModel ridesViewModel;

    CityModel fromCity, toCity;
    public final int startLocation = 1;
    public final int endLocation = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);
        ButterKnife.bind(this);
        setUpActionBar();
        ridesViewModel = ViewModelProviders.of(this).get(RidesViewModel.class);
        ridesViewModel.init();
        setCreateRideObserver();
        setUpUi();
    }

    private void setCreateRideObserver() {
        ridesViewModel.getCreateRideMld().observe(this, (JsonResponse createRideResponse) -> {
            removeProgressDialog();
            if (Objects.requireNonNull(createRideResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(CreateRideActivity.this, createRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(CreateRideActivity.this, createRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpUi() {
        vehicleTypeSP.setAdapter(CarTypeSpinnerUtils.setSpinnerAdapter(this, vehicleTypeSP));
    }


    @OnClick(R.id.et_date_of_journey)
    public void selectDate() {
        DatePickerUtils.startDatePickerWithTodaysDate(CreateRideActivity.this, CreateRideActivity.this);
    }

    @OnClick(R.id.et_time_of_journey)
    public void selectTime() {
        TimePickerUtils.openTimePicker(CreateRideActivity.this, CreateRideActivity.this);
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
            ridesViewModel.getRidesRepository().createRide(ridesViewModel.getCreateRideMld(), getRideDetails());
        }

    }

    @Override
    public void setDateFromDatePicker(String selectedDate) {
        dateOfJourneyET.setText(selectedDate);
    }

    @Override
    public void setTimeFromTimePicker(String selectedTime) {
        timeOfJourneyET.setText(selectedTime);
    }

    @Override
    public void sendTypeOnSelection(VehicleTypeModel data) {

    }

    private boolean validate() {
        if (vehicleTypeSP.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.vehicle_type, this);
            return false;
        } else if (dateOfJourneyET.getText().toString().isEmpty()) {
            return false;
        } else if (timeOfJourneyET.getText().toString().isEmpty()) {
            showMandatoryError(R.string.time_of_journey_hint, this);
            return false;
        } else if (distanceET.getText().toString().trim().isEmpty()) {
            showMandatoryError(R.string.distance_hint, this);
            return false;
        } else if (costOfTripET.getText().toString().trim().isEmpty()) {
            showMandatoryError(R.string.cost_of_trip_hint, this);
            return false;
        }
        return true;
    }

    private CreateRideRequestModel getRideDetails() {
        CreateRideRequestModel rideRequestModel = new CreateRideRequestModel();
        rideRequestModel.setCarType(((VehicleTypeModel) vehicleTypeSP.getSelectedItem()).getType());
        rideRequestModel.setAgencyMobileNo(appPreferences.getUserDetails().mobileNo);
        rideRequestModel.setCost(Integer.parseInt(costOfTripET.getText().toString().trim()));
        rideRequestModel.setDistance(Integer.parseInt(distanceET.getText().toString()));
        rideRequestModel.setFromCity(fromCity);
        rideRequestModel.setToCity(toCity);
        rideRequestModel.setRideType((rideTypeRg.getCheckedRadioButtonId() == R.id.rb_one_way) ? AppConstants.ONE_WAY : AppConstants.TWO_WAY);
        rideRequestModel.setPickupTime("1234");
        rideRequestModel.setDropTime("1234");

        return rideRequestModel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            CityModel cityModel = (CityModel) Objects.requireNonNull(data).getSerializableExtra(IntentConstants.PREFERRED_CITY);
            if(requestCode == startLocation) {
                fromLocET.setText(cityModel.getName());
                fromCity = cityModel;
            } else {
                toLocET.setText(cityModel.getName());
                toCity = cityModel;
            }
        }


    }
}
