package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.RegistrationInterface;
import com.example.cabway.ui.dialogs.DialogOtp;
import com.example.cabway.viewModels.RegistrationViewModel;
import com.example.database.Utills.AppConstants;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;


public class RegistrationActivity extends BaseActivity implements RegistrationInterface {

    @BindView(R.id.first_name)
    EditText etFirstName;

    @BindView(R.id.last_name)
    EditText etLastName;

    @BindView(R.id.phone)
    EditText etPhone;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.et_city)
    EditText etCity;

    @BindView(R.id.et_state)
    EditText etState;

    @BindView(R.id.et_pincode)
    EditText etPincode;

    @BindView(R.id.password)
    EditText etPassword;

    @BindView(R.id.confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.generate_otp)
    Button bGenerateOtp;

    @BindView(R.id.submit)
    Button bSubmit;

    @BindView(R.id.type)
    RadioGroup type;

    private DialogOtp dialogOtp;
    private String mMobileNumber;

    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpActionBar();
        ButterKnife.bind(this);
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        registrationViewModel.init();
        dialogOtp = new DialogOtp(this);
    }

    private void setOtpObserver() {
        registrationViewModel.getOtpResponseMld().observe(this, otpResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(otpResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(RegistrationActivity.this, R.string.otp_sent, Toast.LENGTH_SHORT).show();
                if (!dialogOtp.isShowing())
                    dialogOtp.showCustomDialogVerifyMobile(mMobileNumber);
            } else {
                Toast.makeText(RegistrationActivity.this, otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.generate_otp)
    void generateOtp() {
        requestOtp();
    }

    @OnClick(R.id.submit)
    void submitInfo() {
        if (validateAllFields()) {
            setUserType(type.getCheckedRadioButtonId());
            Toast.makeText(this, "" + mMobileNumber + " ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DocumentListActivity.class));
        }
    }

    private void setUserType(int id) {
        appPreferences.saveLoginType((id == R.id.agency) ? AppConstants.AGENCY : AppConstants.DRIVER);
    }

    @Override
    public void verifyOtp(String enteredOtp) {
        if (enteredOtp.equals("1234")) {
            dialogOtp.otpVerificationSuccess();
            setUiForOtpSuccess();
        } else
            dialogOtp.otpVerificationFailed();
    }

    @Override
    public void requestOtp() {
        mMobileNumber = etPhone.getText().toString().trim();
        if (TextValidationUtils.validateMobileNumber(mMobileNumber)) {
            if (checkNetworkAvailableWithoutError()) {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                setOtpObserver();
                registrationViewModel.getRegistrationRepository().getOtp(registrationViewModel.getOtpResponseMld(), mMobileNumber);
            }
        } else
            Toast.makeText(this, R.string.mobile_length_message, Toast.LENGTH_SHORT).show();

    }


    private void setUiForOtpSuccess() {
        etFirstName.setVisibility(View.VISIBLE);
        etLastName.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        etConfirmPassword.setVisibility(View.VISIBLE);
        etEmail.setVisibility(View.VISIBLE);
        etAddress.setVisibility(View.VISIBLE);
        etCity.setVisibility(View.VISIBLE);
        etState.setVisibility(View.VISIBLE);
        etPincode.setVisibility(View.VISIBLE);
        bSubmit.setVisibility(View.VISIBLE);
        type.setVisibility(View.VISIBLE);
        etPhone.setInputType(InputType.TYPE_NULL);
        etPhone.setKeyListener(null);
        bGenerateOtp.setVisibility(View.GONE);
    }

    private boolean validateAllFields() {
        if (TextValidationUtils.isEmpty(etFirstName.getText().toString())) {
            showMandatoryError(R.string.first_name, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etLastName.getText().toString())) {
            showMandatoryError(R.string.last_name, this);
            return false;
        }else if (TextValidationUtils.isEmpty(etEmail.getText().toString())) {
            showMandatoryError(R.string.email, this);
            return false;
        }else if (!TextValidationUtils.isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextValidationUtils.isEmpty(etAddress.getText().toString())) {
            showMandatoryError(R.string.address, this);
            return false;
        }else if (TextValidationUtils.isEmpty(etCity.getText().toString())) {
            showMandatoryError(R.string.city, this);
            return false;
        }else if (TextValidationUtils.isEmpty(etState.getText().toString())) {
            showMandatoryError(R.string.state, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etPincode.getText().toString())) {
            showMandatoryError(R.string.pincode, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etPassword.getText().toString())) {
            showMandatoryError(R.string.password, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etConfirmPassword.getText().toString())) {
            showMandatoryError(R.string.confirm_password, this);
            return false;
        } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(this, R.string.password_mismatch_message, Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
}
