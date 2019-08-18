package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cabway.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.iv_edit)
    ImageView ivEdit;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_role)
    EditText erRole;

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.sp_state)
    AppCompatSpinner spState;

    @BindView(R.id.sp_city)
    AppCompatSpinner spCity;

    @BindView(R.id.et_pincode)
    EditText etPincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpActionBar();
        ButterKnife.bind(this);
    }
}
