package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.RegistrationInterface;
import com.example.cabway.ui.dialogs.DialogOtp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements RegistrationInterface {

    @BindView(R.id.phone)
    EditText etPhoneNumber;
    @BindView(R.id.ti_phone_number)
    TextInputLayout ti_phone_number;
    @BindView(R.id.ti_new_password)
    TextInputLayout ti_new_password;
    @BindView(R.id.generate_otp)
    Button generate_otp;
    @BindView(R.id.change_password)
    Button change_password;
    @BindView(R.id.message)
    TextView displayMessage;
    private DialogOtp dialogOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setUpActionBar();
        ButterKnife.bind(this);
        dialogOtp = new DialogOtp(this);
    }

    @OnClick(R.id.generate_otp)
    void resetPassword() {
        requestOtp();
    }

    @Override
    public void verifyOtp(String enteredOtp) {
        if (enteredOtp.equals("1234")) {
            dialogOtp.otpVerificationSuccess();
            setUiForOtpSuccess();
        } else
            dialogOtp.otpVerificationFailed();
    }

    private void setUiForOtpSuccess() {
        displayMessage.setVisibility(View.GONE);
        generate_otp.setVisibility(View.GONE);
        ti_phone_number.setVisibility(View.GONE);
        change_password.setVisibility(View.VISIBLE);
        ti_new_password.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestOtp() {
        String mMobileNumber = etPhoneNumber.getText().toString().trim();
        if (TextValidationUtils.validateMobileNumber(mMobileNumber)) {
            Toast.makeText(this, R.string.otp_sent, Toast.LENGTH_SHORT).show();
            if (!dialogOtp.isShowing())
                dialogOtp.showCustomDialogVerifyMobile(mMobileNumber);
        } else
            Toast.makeText(this, R.string.mobile_length_message, Toast.LENGTH_SHORT).show();
    }

}
