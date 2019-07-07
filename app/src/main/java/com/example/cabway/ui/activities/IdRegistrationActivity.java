package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdRegistrationActivity extends BaseActivity implements DatePickerCallBackInterface {

    @BindView(R.id.issued_date)
    TextView tvIssuedDate;
    @BindView(R.id.expired_date)
    TextView tvExpiredDate;
    @BindView(R.id.et_doc_number)
    EditText etLicenseNumber;
    @BindView(R.id.et_license_vehicle_type)
    EditText etVehicleType;
    @BindView(R.id.il_license_number)
    TextInputLayout ti_doc_number;
    @BindView(R.id.il_license_type)
    TextInputLayout ti_name_on_doc;
    @BindView(R.id.expired_layout)
    LinearLayout expireDateLayout;
    @BindView(R.id.issued_layout)
    LinearLayout issuedDateLayout;

    private boolean isAadharCard = false;
    private boolean isIssuedDate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_registration);
        setUpActionBar();
        ButterKnife.bind(this);
        isAadharCard = getIntent().getBooleanExtra(IntentConstants.AADHAR_CART_EXTRA, false);
        setupUi();
    }

    private void setupUi() {
        if (isAadharCard) {
            setTitle(R.string.aadhar_card);
            expireDateLayout.setVisibility(View.GONE);
            issuedDateLayout.setVisibility(View.GONE);
            ti_name_on_doc.setHint(getString(R.string.name_on_document));
        } else {
            setTitle(R.string.driver_license);
            expireDateLayout.setVisibility(View.VISIBLE);
            issuedDateLayout.setVisibility(View.VISIBLE);
            ti_name_on_doc.setHint(getString(R.string.vehicle_type));
        }

    }

    @OnClick({R.id.issued_layout, R.id.expired_layout})
    public void startDatePicker(View view) {
        switch (view.getId()) {
            case R.id.issued_layout:
                isIssuedDate = true;
                break;
            case R.id.expired_layout:
                isIssuedDate = false;
                break;
        }
        DatePickerUtils.startDatePicker(this, this);
    }

    @OnClick(R.id.btn_continue)
    public void onClick(View view) {
        if (validateForm())
            Toast.makeText(this, etLicenseNumber.getText() + " " + etVehicleType.getText() + " " + tvIssuedDate.getText() + " " + tvExpiredDate.getText(), Toast.LENGTH_SHORT).show();
    }

    private boolean validateForm() {
        if (TextValidationUtils.isEmpty(etLicenseNumber.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.document_number, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etVehicleType.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.vehicle_type, this);
            return false;
        } else if (TextValidationUtils.isEmpty(tvIssuedDate.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.issued_on, this);
            return false;
        } else if (TextValidationUtils.isEmpty(tvExpiredDate.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.expiry_date, this);
            return false;
        } else
            return true;

    }

    @Override
    public void setDateFromDatePicker(String selectedDate) {
        if (isIssuedDate)
            tvIssuedDate.setText(selectedDate);
        else
            tvExpiredDate.setText(selectedDate);

    }
}
