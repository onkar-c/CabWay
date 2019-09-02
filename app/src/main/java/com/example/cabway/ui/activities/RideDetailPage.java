package com.example.cabway.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cabway.R;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.core.responseModel.RideResponseModel;
import com.example.database.Utills.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RideDetailPage extends BaseActivity {

    @BindView(R.id.agencyName)
    TextView tvAgencyName;
    @BindView(R.id.journey_date)
    TextView tvRideDate;
    @BindView(R.id.journey_time)
    TextView tvRideTime;
    @BindView(R.id.car_type)
    TextView tvCarType;
    @BindView(R.id.trip_mode)
    TextView tvTripMode;
    @BindView(R.id.agency_number)
    TextView tvAgencyNumber;
    @BindView(R.id.km)
    TextView tvKm;
    @BindView(R.id.start_location)
    TextView tvStartLocation;
    @BindView(R.id.destination_location)
    TextView tvDestinationLocation;
    @BindView(R.id.ride_cost)
    TextView tvRideCost;
    @BindView(R.id.profile_image)
    ImageView ivProfileImage;
    @BindView(R.id.call_agency)
    Button btCall;
    RideResponseModel ride;
    private boolean isFromHistory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ride_detail_page);
        setUpActionBar();
        ButterKnife.bind(this);
        getDataFromExtras();
        setUi();
    }

    private void setUi() {

        tvAgencyName.setText(String.format("%s %s", ride.getAgency().getFirstName(), ride.getAgency().getLastName()));
        tvRideDate.setText(DatePickerUtils.convertDate(ride.getPickupTime()));
        tvRideTime.setText(DatePickerUtils.convertDate(ride.getDropTime()));
        tvCarType.setText(ride.getCarType());
        tvTripMode.setText(ride.getRideType());
        tvAgencyNumber.setText(ride.getAgency().getMobileNo());
        tvKm.setText(String.format("%s ", ride.getDistance().toString()));
        tvStartLocation.setText(ride.getFromCity().getName());
        tvDestinationLocation.setText(ride.getToCity().getName());
        tvRideCost.setText(String.format("%s ", ride.getCost().toString()));
        ImageUtils.setImageFromUrl(this, ride.getAgency().getProfileImage(), ivProfileImage);
        btCall.setVisibility((isFromHistory) ? View.GONE : View.VISIBLE);
    }

    private void getDataFromExtras() {
        isFromHistory = getIntent().getBooleanExtra(IntentConstants.IS_FROM_HISTORY, false);
        ride = (RideResponseModel) getIntent().getSerializableExtra(IntentConstants.RIDE);
    }

    @OnClick(R.id.call_agency)
    void callAgency() {
        String mobileNumber = (TextValidationUtils.isEmpty(ride.getAgency().getMobileNo()) ? AppConstants.DUMMY_AGENCY_number : ride.getAgency().getMobileNo());
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + mobileNumber));
        startActivity(callIntent);
    }
}
