package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cabway.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.phone)
    EditText etUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setUpActionBar();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.reset_password)
    void resetPassword() {
        Toast.makeText(this, "" + etUserName.getText(), Toast.LENGTH_SHORT).show();
    }

}
