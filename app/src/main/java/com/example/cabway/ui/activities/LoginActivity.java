package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.database.Utills.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone)
    EditText etPhoneNumber;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.type)
    RadioGroup type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpActionBar();
        ButterKnife.bind(this);
    }


    @OnClick(R.id.login)
    void verifyCredentials() {
        if (checkNetworkAvailableWithoutError()) {
            setUserType(type.getCheckedRadioButtonId());
            Toast.makeText(this, "" + etPhoneNumber.getText() + " " + etPassword.getText() + " ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DashBoardActivity.class));
        }
    }

    private void setUserType(int id) {
        appPreferences.saveLoginType((id == R.id.agency) ? AppConstants.AGENCY : AppConstants.DRIVER);
    }

    @OnClick(R.id.forgot_password)
    void startForgetPasswordActivity() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    @OnClick(R.id.sign_up)
    void startSignUpActivity() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
