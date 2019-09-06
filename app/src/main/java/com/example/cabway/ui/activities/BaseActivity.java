package com.example.cabway.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.ConnectivityUtils;
import com.example.cabway.Utils.DialogUtils;
import com.example.cabway.fcm.CabWayFCMInstanceService;
import com.example.cabway.viewModels.UserViewModel;
import com.example.core.Utills.AppPreferences;
import com.example.core.responseModel.JsonResponse;
import com.example.database.Utills.AppConstants;


public class BaseActivity extends AppCompatActivity {

    public static BaseActivity instance;
    private final String TAG = "Base activity";
    AppPreferences appPreferences;
    DialogInterface.OnClickListener onExit = (dialog, id) -> finish();
    UserViewModel logoutUserViewModel;
    private ProgressDialog mProgressDialog;
    DialogInterface.OnClickListener onLogoutListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            if (checkNetworkAvailableWithoutError()) {
                logoutUserViewModel.getUserRepository().logout(logoutUserViewModel.getLogoutResponseMld());
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        appPreferences = AppPreferences.getInstance();
        if (appPreferences == null)
            appPreferences = new AppPreferences(this);
        logoutUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        logoutUserViewModel.init();
        setLogoutObserver();
        setProgressDialog(this);
    }

    private void setLogoutObserver() {
        logoutUserViewModel.getLogoutResponseMld().observe(this, (JsonResponse logoutResponse) -> {
            removeProgressDialog();
            if (isSuccessResponse(logoutResponse)) {
                performLogout();
            } else {
                Toast.makeText(this, logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void performLogout() {
        CabWayFCMInstanceService.unregisterFcmTopic(appPreferences.getUserDetails().mobileNo);
        appPreferences.clearPreferencesForLogout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public boolean checkNetworkAvailableWithoutError() {
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

        if (isTaskRoot())
            DialogUtils.showMessageDialog(this, getString(R.string.exit_message), onExit);
        else
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

    protected void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        }
    }

    public void setProgressDialog(Context context) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
        } catch (Exception ignored) {
        }
    }

    public void showProgressDialog(String bodyText, final boolean isRequestCancelable) {
        mProgressDialog.setCancelable(isRequestCancelable);
        mProgressDialog.setMessage(bodyText);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void removeProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception ignored) {

        }
    }

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else {
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    performActionAfterPermission();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    performActionAfterPermission();
                }
                break;
        }
    }

    public boolean isSuccessResponse(JsonResponse jsonResponse) {
        if (jsonResponse.getStatus().equals(AppConstants.AUTH_ERROR)) {
            Toast.makeText(this, jsonResponse.getMessage(), Toast.LENGTH_LONG).show();
            performLogout();
            return false;
        } else return jsonResponse.getStatus().equals(AppConstants.SUCCESS);
    }

    public void performActionAfterPermission() {

    }
}
