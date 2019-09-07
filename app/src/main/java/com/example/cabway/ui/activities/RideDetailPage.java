package com.example.cabway.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.DialogUtils;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.adapter.DriverListAdapter;
import com.example.cabway.viewModels.RidesViewModel;
import com.example.core.CommonModels.UserModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.responseModel.RideResponseModel;
import com.example.database.Utills.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RideDetailPage extends BaseActivity {

    public List<UserModel> driverList = null;
    public RideResponseModel ride;
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
    @BindView(R.id.delete)
    Button btDelete;
    @BindView(R.id.request_ride)
    Button btRequest;
    @BindView(R.id.driverList)
    RecyclerView driverRecyclerView;
    @BindView(R.id.status_layout)
    LinearLayout status_layout;
    @BindView(R.id.status)
    TextView status;

    DriverListAdapter driverListAdapter;
    RidesViewModel ridesViewModel;

    private boolean isFromHistory = false;
    private boolean isAgency = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ride_detail_page);
        setUpActionBar();
        ButterKnife.bind(this);
        ridesViewModel = ViewModelProviders.of(this).get(RidesViewModel.class);
        ridesViewModel.init();
        setDeleteRideObserver();
        setRequestRideObserver();
        getDataFromExtras();
        uiCommon();
    }

    private void setDeleteRideObserver() {
        ridesViewModel.getDeleteRideResponseMld().observe(this, (JsonResponse deleteRideResponse) -> {
            removeProgressDialog();
            if (isSuccessResponse(deleteRideResponse)) {
                Toast.makeText(RideDetailPage.this, deleteRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(RideDetailPage.this, deleteRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRequestRideObserver() {
        ridesViewModel.getRequestRideResponseMld().observe(this, (JsonResponse requestRideResponse) -> {
            removeProgressDialog();
            if (isSuccessResponse(requestRideResponse)) {
                Toast.makeText(RideDetailPage.this, requestRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
                ride = requestRideResponse.getRide();
                uiCommon();
            } else {
                Toast.makeText(RideDetailPage.this, requestRideResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu.findItem(R.id.action_edit) != null)
            menu.findItem(R.id.action_edit).setVisible((isAgency && !ride.getStatus().equals(AppConstants.ACCEPTED)));
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            editRide();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setButtons() {
        if (isAgency) {
            if (!ride.getStatus().equals(AppConstants.ACCEPTED)) {
                btDelete.setVisibility(View.VISIBLE);
            } else
                btDelete.setVisibility(View.GONE);
                btCall.setVisibility(View.GONE);
                status_layout.setVisibility(View.GONE);
        } else {
            btCall.setVisibility(View.VISIBLE);
            status_layout.setVisibility((!ride.getStatus().equals(AppConstants.NEW)) ? View.VISIBLE : View.GONE);
            btRequest.setVisibility((isFromHistory || !ride.getStatus().equals(AppConstants.NEW)) ? View.GONE : View.VISIBLE);
            status.setText(ride.getStatus());
        }

        invalidateOptionsMenu();
    }

    private void prepareDriverList() {
        if (isAgency) {
            if (ride.getStatus().equals(AppConstants.REQUESTED))
                driverList = ride.getDriverList();
            if (ride.getStatus().equals(AppConstants.ACCEPTED) && driverList == null && ride.getDriver() != null) {
                driverList = new ArrayList<>();
                driverList.add(ride.getDriver());
            }
            if (driverList != null && driverList.size() > 0) {
                driverRecyclerView.setVisibility(View.VISIBLE);
                setUpDriverList();
            } else
                driverRecyclerView.setVisibility(View.GONE);
        }
    }

    private void setUpDriverList() {
        if (driverListAdapter != null)
            driverListAdapter.resetData(driverList);
        else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            driverRecyclerView.setLayoutManager(layoutManager);
            driverListAdapter = new DriverListAdapter(this, driverList);
            driverRecyclerView.setAdapter(driverListAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUi() {

        tvAgencyName.setText(ride.getAgency().getAgencyName());
        tvRideDate.setText(DatePickerUtils.convertDate(ride.getPickupTime()));
        tvRideTime.setText(DatePickerUtils.convertDate(ride.getDropTime()));
        tvCarType.setText(ride.getCarType());
        tvTripMode.setText(ride.getRideType());
        tvAgencyNumber.setText(ride.getAgency().getMobileNo());
        tvKm.setText(String.format("%s ", ride.getDistance().toString()));
        tvStartLocation.setText(ride.getFromCity().getName());
        tvDestinationLocation.setText(ride.getToCity().getName());
        tvRideCost.setText(String.format("%s ", ride.getCost().toString()));
        if(appPreferences.getUserDetails().role.equals(AppConstants.AGENCY)){
            tvAgencyName.setVisibility(View.GONE);
            ivProfileImage.setVisibility(View.GONE);
        } else {
            tvAgencyName.setVisibility(View.VISIBLE);
            ImageUtils.setImageFromUrl(this, ride.getAgency().getProfileImage(), ivProfileImage);
        }
    }

    private void getDataFromExtras() {
        isFromHistory = getIntent().getBooleanExtra(IntentConstants.IS_FROM_HISTORY, false);
        ride = (RideResponseModel) getIntent().getSerializableExtra(IntentConstants.RIDE);
        isAgency = appPreferences.getUserDetails().role.equals(AppConstants.AGENCY);
    }

    @OnClick(R.id.call_agency)
    void callAgency() {
        String mobileNumber = (TextValidationUtils.isEmpty(ride.getAgency().getMobileNo()) ? AppConstants.DUMMY_AGENCY_number : ride.getAgency().getMobileNo());
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + mobileNumber));
        startActivity(callIntent);
    }


    void editRide() {
        Intent nextActivity = new Intent(this, CreateRideActivity.class);
        nextActivity.putExtra(IntentConstants.RIDE, ride);
        startActivityForResult(nextActivity, AppConstants.REFRESH);
    }

    @OnClick(R.id.delete)
    void deleteRide() {
        if (checkNetworkAvailableWithoutError()) {
            String message = String.format(getString(R.string.accept_reject_message), AppConstants.DELETE);
            DialogUtils.showMessageDialog(this, message, (dialog, id) -> {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                ridesViewModel.getRidesRepository().deleteRide(ridesViewModel.getDeleteRideResponseMld(), ride.getRideId());
            });
        }
    }


    @OnClick(R.id.request_ride)
    void requestRide() {
        if (checkNetworkAvailableWithoutError()) {
            String message = String.format(getString(R.string.accept_reject_message), AppConstants.REQUEST);
            DialogUtils.showMessageDialog(this, message, (dialog, id) -> {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                ridesViewModel.getRidesRepository().requestRide(ridesViewModel.getRequestRideResponseMld(), ride.getRideId());
            });
        }
    }

    private void uiCommon() {
        setUi();
        prepareDriverList();
        setButtons();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            RideResponseModel lRide = (RideResponseModel) Objects.requireNonNull(data).getSerializableExtra(IntentConstants.RIDE);
            if (requestCode == AppConstants.REFRESH) {
                ride = lRide;
                uiCommon();
            }
        }
    }
}
