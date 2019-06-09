package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.ConnectivityUtils;

public class BaseActivity extends AppCompatActivity {

    protected LinearLayout loadingProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void initBaseViews(){
        loadingProgressBar = findViewById(R.id.lloadingProgressBar);
    }

    protected void showLoadingDialogue(){
        if(loadingProgressBar != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            loadingProgressBar.setVisibility(View.VISIBLE);
        }
    }

    protected void dismissLoadingDialogue(){
        if(loadingProgressBar != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public boolean checkNetworkAvailableWithError() {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.error_fail_connection, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isNetworkAvailable() {
        return ConnectivityUtils.isNetworkAvailable(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
