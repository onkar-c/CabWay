package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.RegistrationInterface;
import com.example.cabway.ui.dialogs.DialogOtp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;


public class RegistrationActivity extends BaseActivity implements RegistrationInterface {

    @BindView(R.id.first_name)
    EditText etFirstName;

    @BindView(R.id.last_name)
    EditText etLastName;

    @BindView(R.id.phone)
    EditText etPhone;

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
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpActionBar();
        ButterKnife.bind(this);
        dialogOtp = new DialogOtp(this);
    }

    @OnCheckedChanged({R.id.agency, R.id.driver})
    void onLoginTypeSelected(RadioButton button, boolean checked) {
        if (checked)
            userType = button.getText().toString();
    }

    @OnClick(R.id.generate_otp)
    void generateOtp() {
        requestOtp();
    }

    @OnClick(R.id.submit)
    void submitInfo() {
        if (validateAllFields()) {
            Toast.makeText(this, "" + mMobileNumber + " " + userType, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DocumentListActivity.class));
        }
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
            Toast.makeText(this, R.string.otp_sent, Toast.LENGTH_SHORT).show();
            if (!dialogOtp.isShowing())
                dialogOtp.showCustomDialogVerifyMobile(mMobileNumber);
        } else
            Toast.makeText(this, R.string.mobile_length_message, Toast.LENGTH_SHORT).show();

    }


    private void setUiForOtpSuccess() {
        etFirstName.setVisibility(View.VISIBLE);
        etLastName.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        etConfirmPassword.setVisibility(View.VISIBLE);
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
