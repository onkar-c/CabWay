package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(SplashScreenActivity.this::startDashboardActivity, 1000);
    }

    private void startDashboardActivity() {
        Intent nextActivity;
        if (appPreferences.isLogin())
            nextActivity = new Intent(this, DashBoardActivity.class);
        else
            nextActivity = new Intent(this, LoginActivity.class);
        startActivity(nextActivity);
        finish();
    }
}
