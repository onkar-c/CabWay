package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.RegistrationInterface;
import com.example.cabway.ui.dialogs.DialogOtp;
import com.example.cabway.viewModels.ForgotPasswordViewModel;
import com.example.database.Utills.AppConstants;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;

public class ForgotPasswordActivity extends BaseActivity implements RegistrationInterface {

    @BindView(R.id.phone)
    EditText etPhoneNumber;
    @BindView(R.id.new_password)
    EditText etNewPassword;
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
    private String mMobileNumber;

    private ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setUpActionBar();
        ButterKnife.bind(this);
        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        forgotPasswordViewModel.init();
        setObservers();

    }

    private void setObservers() {
        setOtpObserver();
        setVerifyOtpObserver();
        setResetPasswordObserver();
    }


    private void setOtpObserver() {
        forgotPasswordViewModel.getOtpResponseMld().observe(this, otpResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(otpResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(ForgotPasswordActivity.this, R.string.otp_sent, Toast.LENGTH_SHORT).show();
                dialogOtp = new DialogOtp(this);
                if (!dialogOtp.isShowing())
                    dialogOtp.showCustomDialogVerifyMobile(mMobileNumber);
            } else {
                Toast.makeText(ForgotPasswordActivity.this, otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVerifyOtpObserver() {
        forgotPasswordViewModel.getVerifyOtpResponseMld().observe(this, verifyOtpResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(verifyOtpResponse).getStatus().equals(AppConstants.SUCCESS)) {
                dialogOtp.otpVerificationSuccess();
                setUiForOtpSuccess();
            } else
                dialogOtp.otpVerificationFailed();
        });
    }

    private void setResetPasswordObserver() {
        forgotPasswordViewModel.getResetPasswordResponseMld().observe(this, resetPasswordResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(resetPasswordResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(ForgotPasswordActivity.this, resetPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(ForgotPasswordActivity.this, resetPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.generate_otp)
    void generateOtp() {
        requestOtp();
    }

    @Override
    public void verifyOtp(String enteredOtp) {
        if (checkNetworkAvailableWithoutError()) {
            showProgressDialog(AppConstants.PLEASE_WAIT, false);
            forgotPasswordViewModel.getForgotPasswordRepository().verifyOtp(forgotPasswordViewModel.getVerifyOtpResponseMld(), mMobileNumber, Integer.parseInt(enteredOtp));
        }
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
        mMobileNumber = etPhoneNumber.getText().toString().trim();
        if (TextValidationUtils.validateMobileNumber(mMobileNumber)) {
            if (checkNetworkAvailableWithoutError()) {
                forgotPasswordViewModel.getForgotPasswordRepository().getOtp(forgotPasswordViewModel.getOtpResponseMld(), mMobileNumber);
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
            }
        } else
            Toast.makeText(this, R.string.mobile_length_message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.change_password)
    public void Change_password() {
        String newPassword = etNewPassword.getText().toString();
        if (!TextUtils.isEmpty(newPassword)) {
            if (checkNetworkAvailableWithoutError()) {
                forgotPasswordViewModel.getForgotPasswordRepository().resetPassword(forgotPasswordViewModel.getResetPasswordResponseMld(), mMobileNumber, newPassword);
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
            }
        } else
            showMandatoryError(R.string.password, this);
    }

}
