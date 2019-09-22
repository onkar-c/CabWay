package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.fcm.CabWayFCMInstanceService;
import com.example.cabway.viewModels.LoginViewModel;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;

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
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
    }

    private void setObservers() {
        setLoginResponseObserver();
    }

    private void setLoginResponseObserver() {
        loginViewModel.getLoginResponse().observe(this, loginResponse -> {
            LoginActivity.this.removeProgressDialog();
            if (isSuccessResponse(loginResponse)) {
//                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setUserData(loginResponse.getUser());
                setCabwayInfo(loginResponse.getCabWayEmail(), loginResponse.getCabWayNumber());
                startNextActivity(loginResponse.getUser());
            } else {
                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setUserData(UserModel user) {
        appPreferences.setIsLogin(true);
        appPreferences.setAuthKey(user.authKey);
        appPreferences.setUserDetails(user);
    }

    private void setCabwayInfo(String email, String number){
        appPreferences.setCabWayEmail(email);
        appPreferences.setCabWayNumber(number);
    }

    private void startNextActivity(UserModel user) {
        Intent nextActivityIntent;
        if (user.documentCompleted) {
            nextActivityIntent = new Intent(LoginActivity.this, DashBoardActivity.class);
            CabWayFCMInstanceService.registerFcmTopic(user.mobileNo);
        } else {
            nextActivityIntent = new Intent(LoginActivity.this, DocumentListActivity.class);
            nextActivityIntent.putExtra(IntentConstants.IS_FROM_LOGIN, true);
        }
        startActivity(nextActivityIntent);
        finish();
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
