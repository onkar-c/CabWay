package com.example.cabway.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.ConnectivityUtils;
import com.example.cabway.Utils.DialogUtils;
import com.example.core.Utills.AppPreferences;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    AppPreferences appPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferences = AppPreferences.getInstance();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

        if(isTaskRoot())
            DialogUtils.showExitDialog(this);
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

    protected void setUpActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        }
    }


    public void showProgressDialog(Context context,String bodyText, final boolean isRequestCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mProgressDialog.setCancelable(isRequestCancelable);
                mProgressDialog.setOnKeyListener((dialog, keyCode, event) -> {
                    if (keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_SEARCH) {
                        return true; //
                    } else if (keyCode == KeyEvent.KEYCODE_BACK && isRequestCancelable) {
                        Log.e("Ondailogback", "cancel dailog");
//                        RequestManager.cancelRequest();
                        dialog.dismiss();
                        return true;
                    }
                    return false;
                });
            }
            mProgressDialog.setMessage(bodyText);

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception ignored) {
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

}
