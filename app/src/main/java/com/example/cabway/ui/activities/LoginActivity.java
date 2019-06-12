package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.cabway.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone_or_email)
    EditText etUserName;
    @BindView(R.id.password)
    EditText etPassword;


    private String userLoginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initBaseViews();
        setUpActionBar();
        ButterKnife.bind(this);
    }


    @OnCheckedChanged({R.id.agency, R.id.driver})
    void onLoginTypeSelected(RadioButton button, boolean checked) {
        if(checked)
        userLoginType = button.getText().toString();
    }

    @OnClick(R.id.login)
    void verifyCredentials() {
        if(checkNetworkAvailableWithError())
        Toast.makeText(this, "" + etUserName.getText() + " " + etPassword.getText() + " " + userLoginType, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.forgot_password)
    void startForgetPasswordActivity() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    @OnClick(R.id.sign_up)
    void startSignUpActivity() {
        Toast.makeText(this, "Signup clicked", Toast.LENGTH_SHORT).show();
    }
}
