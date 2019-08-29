package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.viewModels.SplashViewModel;
import com.example.database.Utills.AppConstants;

import java.util.Objects;

public class SplashScreenActivity extends BaseActivity {

    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.init();
        setStateCityObserver();
        splashViewModel.getSplashRepository().getStateCity(splashViewModel.getStateCityResponseMld());


    }

    private void setStateCityObserver() {
        splashViewModel.getStateCityResponseMld().observe(this, stateCityResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(stateCityResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(SplashScreenActivity.this, stateCityResponse.getMessage(), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(SplashScreenActivity.this::startDashboardActivity, 500);
            } else {
                Toast.makeText(SplashScreenActivity.this, stateCityResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startDashboardActivity() {
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
