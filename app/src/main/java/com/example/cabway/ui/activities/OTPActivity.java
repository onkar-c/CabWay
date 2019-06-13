package com.example.cabway.ui.activities;

import android.os.Bundle;

import com.example.cabway.R;
import com.example.cabway.ui.dialogs.DialogOtp;

import butterknife.ButterKnife;

public class OTPActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        setUpActionBar();
        ButterKnife.bind(this);

        DialogOtp dialogOtp = new DialogOtp(this);
        dialogOtp.showCustomDialogVerifyMobile("6565590483");

    }
}
