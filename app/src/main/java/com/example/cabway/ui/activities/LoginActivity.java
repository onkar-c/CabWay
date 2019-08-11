package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.viewModels.LoginViewModel;
import com.example.database.Utills.AppConstants;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone)
    EditText etPhoneNumber;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.type)
    RadioGroup type;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init();
        setObservers();
    }

    private void setObservers() {
        setLoginResponseObserver();
    }

    private void setLoginResponseObserver() {
        loginViewModel.getLoginResponse().observe(this, loginResponse -> {
            LoginActivity.this.removeProgressDialog();
            if (Objects.requireNonNull(loginResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.login)
    void verifyCredentials() {
        String mMobileNumber = etPhoneNumber.getText().toString().trim();
        if (validateAllFields()) {
            if (TextValidationUtils.validateMobileNumber(mMobileNumber)) {
                if (checkNetworkAvailableWithoutError()) {
                    String role = (type.getCheckedRadioButtonId() == R.id.agency) ? AppConstants.AGENCY : AppConstants.DRIVER;
                    showProgressDialog(AppConstants.PLEASE_WAIT, false);
                    loginViewModel.getLoginRepository().validateLogin(loginViewModel.getLoginResponse(), etPhoneNumber.getText().toString(), etPassword.getText().toString(), role);
                }
            } else
                Toast.makeText(this, R.string.mobile_length_message, Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.forgot_password)
    void startForgetPasswordActivity() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    @OnClick(R.id.sign_up)
    void startSignUpActivity() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    private boolean validateAllFields() {
        if (TextValidationUtils.isEmpty(etPhoneNumber.getText().toString())) {
            showMandatoryError(R.string.login_phone, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etPassword.getText().toString())) {
            showMandatoryError(R.string.password, this);
            return false;
        } else
            return true;
    }
}
