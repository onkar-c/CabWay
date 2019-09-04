package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cabway.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriverDetailsActivity extends BaseActivity {

    @BindView(R.id.uploadImageLayout)
    FrameLayout flProfile;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.tv_driver_name)
    TextView tvName;

    @BindView(R.id.tv_mobile_no)
    TextView tvMobileNo;

    @BindView(R.id.et_role)
    TextView tvEmail;

    @BindView(R.id.et_phone_number)
    TextView tvAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        setUpActionBar();
        ButterKnife.bind(this);
    }
}
