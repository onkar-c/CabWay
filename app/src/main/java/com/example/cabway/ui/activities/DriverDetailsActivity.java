package com.example.cabway.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.DialogUtils;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.viewModels.RidesViewModel;
import com.example.core.CommonModels.UserModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.responseModel.RideResponseModel;
import com.example.database.Utills.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DriverDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;

    @BindView(R.id.tv_driver_name)
    TextView tvName;

    @BindView(R.id.tv_mobile_no)
    TextView tvMobileNo;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.accept_driver)
    Button bt_accept_ride;

    @BindView(R.id.reject_driver)
    Button bt_reject_ride;
    RidesViewModel ridesViewModel;
    private UserModel driver;
    private RideResponseModel mRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        setUpActionBar();
        ButterKnife.bind(this);
        ridesViewModel = ViewModelProviders.of(this).get(RidesViewModel.class);
        ridesViewModel.init();
        driver = (UserModel) getIntent().getSerializableExtra(IntentConstants.DRIVER_DETAILS);
        mRide = (RideResponseModel) getIntent().getSerializableExtra(IntentConstants.RIDE);
        setAcceptRejectRidesObserver();
        setUpUi();
    }

    private void setUpUi() {
        ImageUtils.setImageFromUrl(this, driver.profileImage, ivProfile);
        tvName.setText(String.format("%s %s", driver.firstName, driver.lastName));
        tvMobileNo.setText(driver.mobileNo);
        tvEmail.setText(driver.email);
        tvAddress.setText(String.format("%s %s %s %s", driver.address, driver.cityCode.getName(), driver.cityCode.getState().getName(), driver.pinCode));
        bt_accept_ride.setVisibility(View.GONE);
        bt_reject_ride.setVisibility(View.GONE);
        if (mRide.getStatus().equals(AppConstants.REQUESTED)) {
            bt_accept_ride.setVisibility(View.VISIBLE);
            bt_reject_ride.setVisibility(View.VISIBLE);
        }
    }

    private void setAcceptRejectRidesObserver() {
        ridesViewModel.getAcceptRejectRidesResponseMld().observe(this, (JsonResponse acceptRejectRidesResponse) -> {
            removeProgressDialog();
            if (isSuccessResponse(acceptRejectRidesResponse)) {
                Toast.makeText(DriverDetailsActivity.this, acceptRejectRidesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                sendResultData(acceptRejectRidesResponse.getRide());
            } else {
                Toast.makeText(DriverDetailsActivity.this, acceptRejectRidesResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendResultData(RideResponseModel pRide) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(IntentConstants.RIDE, pRide);
        setResult(RESULT_OK, returnIntent);
        finish();
    }


    @OnClick(R.id.call_driver)
    void callAgency() {
        String mobileNumber = (TextValidationUtils.isEmpty(driver.mobileNo) ? AppConstants.DUMMY_AGENCY_number : driver.mobileNo);
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + mobileNumber));
        startActivity(callIntent);
    }

    @OnClick({R.id.accept_driver, R.id.reject_driver})
    void acceptRejectCall(View view) {
        if (checkNetworkAvailableWithoutError()) {
            String message = String.format(getString(R.string.accept_reject_message), (view.getId() == R.id.accept_driver) ? AppConstants.ACCEPT : AppConstants.REJECT);
            DialogUtils.showMessageDialog(this, message, (dialog, id) -> {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                ridesViewModel.getRidesRepository().acceptRejectRide(ridesViewModel.getAcceptRejectRidesResponseMld(),
                        mRide.getRideId(),
                        driver.uuId,
                        (view.getId() == R.id.accept_driver) ? AppConstants.ACCEPT : AppConstants.REJECT);
            });
        }
    }
}
