package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.SpinnerUtils;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.cabway.viewModels.PreferredCityViewModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;

public class PreferredCityActivity extends BaseActivity implements CitySpinnerAdapter.ItemSelectedCallback {
    @BindView(R.id.sp_city)
    AppCompatSpinner spCity;

    @BindView(R.id.sp_state)
    AppCompatSpinner spState;

    @BindView(R.id.description)
    TextView description;
    PreferredCityViewModel preferredCityViewModel;

    private boolean isFromCreateRide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_city);
        setUpActionBar();
        ButterKnife.bind(this);
        isFromCreateRide = getIntent().getBooleanExtra(IntentConstants.IS_FROM_CREATE_RIDE, false);
        setUpUi();
        preferredCityViewModel = ViewModelProviders.of(this).get(PreferredCityViewModel.class);
        preferredCityViewModel.init();
        setPreferredCityObserver();
    }

    private void setPreferredCityObserver() {
        preferredCityViewModel.getPreferredCityResponseMld().observe(this, preferredCityResponse -> {
            removeProgressDialog();
            if (isSuccessResponse(preferredCityResponse)) {
                UserModel userModel = appPreferences.getUserDetails();
                userModel.cityPreferences = preferredCityResponse.getUser().cityPreferences;
                appPreferences.setUserDetails(userModel);
                Toast.makeText(PreferredCityActivity.this, R.string.city_updated_success, Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(PreferredCityActivity.this, preferredCityResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.submit)
    public void updatePreferredCity() {
        if(validate()) {
            if (isFromCreateRide) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(IntentConstants.PREFERRED_CITY, (CityModel) spCity.getSelectedItem());
                setResult(RESULT_OK, returnIntent);
                finish();
            } else {
                if (checkNetworkAvailableWithoutError()) {
                    showProgressDialog(AppConstants.PLEASE_WAIT, false);
                    preferredCityViewModel.getPreferredCityRepository().updatePreferredCity(preferredCityViewModel.getPreferredCityResponseMld(), (CityModel) spCity.getSelectedItem());
                }
            }
        }
    }


    private void setUpUi() {
        spCity.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.CITY, 0, spCity));
        spState.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.STATE, 0, spState));
        description.setText(getString(isFromCreateRide ? R.string.please_select_city : R.string.preferred_city_description));
    }

    private boolean validate() {

        if (spState.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.state, this);
            return false;
        } else if (spCity.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.city, this);
            return false;
        }
        return true;
    }

    @Override
    public <T> void sendDataOnSelection(T data) {
        if (data instanceof StateModel) {
            spCity.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.CITY, ((StateModel) data).getId(), spCity));
        } else if (data instanceof CityModel) {
        }
    }
}
