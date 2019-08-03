package com.example.cabway.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cabway.R;
import com.example.cabway.Utils.IntentConstants;
import com.example.database.Utills.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverRideDetailPage extends BaseActivity {

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
    @BindView(R.id.profile_image)
    ImageView ivProfileImage;
    @BindView(R.id.call_agency)
    Button btCall;

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
        btCall.setVisibility((isFromHistory) ? View.GONE : View.VISIBLE);
    }

    private void getDataFromExtras() {
        isFromHistory = getIntent().getBooleanExtra(IntentConstants.IS_FROM_HISTORY, false);
    }

    @OnClick(R.id.call_agency)
    void callAgency() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + AppConstants.DUMMY_AGENCY_number));
        startActivity(callIntent);
    }
}
