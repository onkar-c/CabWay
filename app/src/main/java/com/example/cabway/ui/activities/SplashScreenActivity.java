package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.cabway.R;
import com.example.core.Utills.CoreSharedHelper;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new CoreSharedHelper(this);
        startDashboardActivity();

    }

    private void startDashboardActivity(){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
