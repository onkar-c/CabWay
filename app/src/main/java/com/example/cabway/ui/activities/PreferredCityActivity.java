package com.example.cabway.ui.activities;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.SpinnerUtils;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.cabway.viewModels.PreferredCityViewModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;

public class PreferredCityActivity extends BaseActivity implements CitySpinnerAdapter.ItemSelectedCallback {
    @BindView(R.id.sp_city)
    AppCompatSpinner spCity;

    @BindView(R.id.sp_state)
    AppCompatSpinner spState;
    PreferredCityViewModel preferredCityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_city);
        setUpActionBar();
        ButterKnife.bind(this);
        setUpUi();
        preferredCityViewModel = ViewModelProviders.of(this).get(PreferredCityViewModel.class);
        preferredCityViewModel.init();
        setPreferredCityObserver();
    }

    private void setPreferredCityObserver() {
        preferredCityViewModel.getPreferredCityResponseMld().observe(this, preferredCityResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(preferredCityResponse).getStatus().equals(AppConstants.SUCCESS)) {
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
    public void updatePreferredCity(){
        if (validate()) {
            if (checkNetworkAvailableWithoutError()) {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                preferredCityViewModel.getPreferredCityRepository().updatePreferredCity(preferredCityViewModel.getPreferredCityResponseMld(), SpinnerUtils.getCityData(spCity.getSelectedItemPosition()));
            }
        }
    }


    private void setUpUi() {
        spCity.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.CITY, 0,spCity));
        spState.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.STATE, 0,spState));
    }

    private boolean validate(){

        if (spCity.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.city, this);
            return false;
        } else if (spState.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.state, this);
            return false;
        }
        return true;
    }

    @Override
    public <T> void sendDataOnSelection(T data) {
        if (data instanceof StateModel) {
            spCity.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.CITY, ((StateModel) data).getId(),spCity));
        } else if (data instanceof CityModel) {
        }
    }
}
