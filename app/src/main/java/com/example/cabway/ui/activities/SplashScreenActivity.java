package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.core.Utills.CoreSharedHelper;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new CoreSharedHelper(this);
        new Handler().postDelayed(SplashScreenActivity.this::startDashboardActivity,1000);
    }

    private void startDashboardActivity(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
