package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopRegistrationActivity extends BaseActivity implements DatePickerCallBackInterface {

    @BindView(R.id.shop_act_expired_date)
    TextView tvShopActExpiredDate;
    @BindView(R.id.et_gst_expired_date)
    TextView tvGstExpiredDate;
    @BindView(R.id.et_shop_act_number)
    EditText etShopactNumber;
    @BindView(R.id.et_name_of_shop)
    EditText etShopName;
    @BindView(R.id.et_owner_name)
    EditText etOwnerName;
    @BindView(R.id.et_gst_number)
    EditText etGstNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_registration);
        setUpActionBar();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.shop_act_expired_date, R.id.gst_expired_date})
    public void startDatePicker(View view) {
        DatePickerUtils.startDatePicker(this, this);
    }


    @OnClick(R.id.btn_continue)
    public void onClick(View view) {
        if (validateForm())
            Toast.makeText(this, "completed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "please enter all the details", Toast.LENGTH_SHORT).show();

    }

    private boolean validateForm() {
        if (TextValidationUtils.isEmpty(etShopactNumber.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.registration_number, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etGstNumber.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.name_on_document, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etOwnerName.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.expiry_date, this);
            return false;
        } else
            return true;

    }


    @Override
    public void setDateFromDatePicker(String selectedDate) {

    }
}
