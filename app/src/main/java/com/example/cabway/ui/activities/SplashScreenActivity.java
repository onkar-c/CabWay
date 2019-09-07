package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.crashlytics.android.Crashlytics;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.viewModels.SplashViewModel;
import com.example.core.responseModel.JsonResponse;

import io.fabric.sdk.android.Fabric;

public class SplashScreenActivity extends BaseActivity {

    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.init();
        setStateCityObserver();
//        generateFcmTopic();
        splashViewModel.getSplashRepository().getStateCity(splashViewModel.getStateCityResponseMld());
//        new Handler().postDelayed(SplashScreenActivity.this::startNextActivity, 500);

    }

    private void setStateCityObserver() {
        splashViewModel.getStateCityResponseMld().observe(this, (JsonResponse stateCityResponse) -> {
            removeProgressDialog();
            if (isSuccessResponse(stateCityResponse)) {
                if (stateCityResponse.getCityList() != null)
                    appPreferences.setCityList(stateCityResponse.getCityList());
                if (stateCityResponse.getStateList() != null)
                    appPreferences.setStateList(stateCityResponse.getStateList());
                if (stateCityResponse.getVehicleList() != null)
                    appPreferences.setVehicleList(stateCityResponse.getVehicleList());
                new Handler().postDelayed(SplashScreenActivity.this::startNextActivity, 500);
            } else {
                Toast.makeText(SplashScreenActivity.this, stateCityResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startNextActivity() {
        Intent nextActivity;
        if (appPreferences.isLogin()) {
            if (appPreferences.getUserDetails().documentCompleted)
                nextActivity = new Intent(this, DashBoardActivity.class);
            else {
                nextActivity = new Intent(this, DocumentListActivity.class);
                nextActivity.putExtra(IntentConstants.IS_FROM_LOGIN, true);
            }
        } else
            nextActivity = new Intent(this, LoginActivity.class);
        startActivity(nextActivity);
        finish();
    }
}
