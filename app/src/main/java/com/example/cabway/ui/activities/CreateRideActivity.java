package com.example.cabway.ui.activities;

import android.os.Bundle;

import com.example.cabway.Utils.CarTypeSpinnerUtils;
import com.example.cabway.ui.adapter.CarTypeSpinnerAdapter;
import com.example.cabway.viewModels.CreateRideViewModel;
import com.example.core.CommonModels.VehicleTypeModel;
import com.example.core.responseModel.JsonResponse;
import com.example.database.Utills.AppConstants;
import com.google.android.material.textfield.TextInputLayout;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.TimePickerUtils;
import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;
import com.example.cabway.ui.Interfaces.TimePickerCallBackInterface;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;

public class CreateRideActivity extends BaseActivity implements DatePickerCallBackInterface, TimePickerCallBackInterface, CarTypeSpinnerAdapter.TypeSelectedCallback {

    @BindView(R.id.et_home_loc)
    EditText fromLocET;

    @BindView(R.id.et_office_loc)
    EditText toLocET;

    @BindView(R.id.tv_vehicle_type_hint)
    TextView carTypeHintTV;

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

    @BindView(R.id.et_agency_mob_no)
    EditText agencyMobNoET;

    @BindView(R.id.et_trip_mode)
    EditText tripModeET;

    @BindView(R.id.et_cost_of_trip)
    EditText costOfTripET;

    @BindView(R.id.et_distance)
    EditText distanceET;

    @BindView(R.id.btn_book_ride)
    Button bookRideBtn;

    @BindView(R.id.rb_one_way)
    RadioButton oneWayRb;

    @BindView(R.id.rb_two_way)
    RadioButton returnRb;

    @BindView(R.id.rg_ride_type)
    RadioGroup rideTypeRg;

    CreateRideViewModel createRideViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);
        ButterKnife.bind(this);
        setUpActionBar();
        createRideViewModel= ViewModelProviders.of(this).get(CreateRideViewModel.class);
        createRideViewModel.init();
        setCreateRideObserver();
        setUpUi();
    }

    private void setCreateRideObserver() {
        createRideViewModel.getCreateRideMld().observe(this,(JsonResponse createRideResponse) ->{
            removeProgressDialog();
            if (Objects.requireNonNull(createRideResponse).getStatus().equals(AppConstants.SUCCESS)) {
                if (createRideResponse.getCityList() != null){

                }

            } else {
                Toast.makeText(CreateRideActivity.this, createRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } );
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

    @OnClick(R.id.et_home_loc)
    public void onClickOfFromLocEditText() {

    }

    @OnClick(R.id.et_office_loc)
    public void onClickOfToLocEditText() {

    }

    @OnClick(R.id.btn_book_ride)
    public void bookRide() {
//        CreateRideRequestModel createRideRequestModel=getRideDetails();
//        if (checkNetworkAvailableWithoutError() && validate()) {
//            showProgressDialog(AppConstants.PLEASE_WAIT, false);
//            createRideViewModel.getCreateRideRepository().createRide(createRideViewModel.getCreateRideMld(),createRideRequestModel);
//        }

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

    private boolean validate(){
//        if(rideTypeRg.i)
        if(vehicleTypeSP.getSelectedItemPosition()==0){
            showMandatoryError(R.string.vehicle_type, this);
            return false;
        }else if(dateOfJourneyET.getText().toString().isEmpty()){
            return false;
        }else if(timeOfJourneyET.getText().toString().isEmpty()){
            showMandatoryError(R.string.time_of_journey_hint, this);
            return false;
        }else if(agencyMobNoET.getText().toString().trim().isEmpty()){
            showMandatoryError(R.string.agency_number_hint, this);
            return false;
        }else if(tripModeET.getText().toString().trim().isEmpty()){
            showMandatoryError(R.string.trip_mode_hint, this);
            return false;
        }else if(distanceET.getText().toString().trim().isEmpty()){
            showMandatoryError(R.string.distance_hint, this);
            return false;
        }else if(costOfTripET.getText().toString().trim().isEmpty()){
            showMandatoryError(R.string.cost_of_trip_hint, this);
            return false;
        }
        return true;
    }

//    private CreateRideRequestModel getRideDetails(){
//        CreateRideRequestModel rideRequestModel=new CreateRideRequestModel();
//        rideRequestModel.setCarType(((VehicleTypeModel) vehicleTypeSP.getSelectedItem()).getType());
//        rideRequestModel.setAgencyId(appPreferences.getUserDetails().uuId);
//        rideRequestModel.setAgencyMobileNo(agencyMobNoET.getText().toString().trim());
//        rideRequestModel.setCost(Integer.parseInt(costOfTripET.getText().toString().trim()));
//        rideRequestModel.setDistance(Integer.parseInt(distanceET.getText().toString()));
//
//        rideRequestModel.setFromCity(fromLocET.getText().toString());
//        rideRequestModel.setToCity(toLocET.getText().toString());
//        rideRequestModel.setRideType(oneWayRb.isChecked() ?"OneWay":"Return");
//        rideRequestModel.setStatus("New");
//        rideRequestModel.setPickupTime();
//
//        return rideRequestModel;
//    }
}
