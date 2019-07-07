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

public class VehicleRegistrationActivity extends BaseActivity implements DatePickerCallBackInterface {

    @BindView(R.id.expired_date)
    TextView tvExpiredDate;
    @BindView(R.id.et_vehicle_registration_number)
    EditText etVehicleRegistrationNumber;
    @BindView(R.id.et_state_name)
    EditText etStateNames;
    @BindView(R.id.il_states_name)
    TextInputLayout ilStates;
    @BindView(R.id.il_doc_desc)
    TextInputLayout ilDescription;
    @BindView(R.id.expired_layout)
    LinearLayout dateLayout;
    @BindView(R.id.et_name_on_doc)
    EditText etNameOnDoc;

    private String docType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration);
        setUpActionBar();
        ButterKnife.bind(this);
        docType = getIntent().getStringExtra(IntentConstants.DOC_TYPE_EXTRA);
        setUi();
    }

    private void setUi() {

        setTitle(docType);
        dateLayout.setVisibility((docType.equals(getString(R.string.vehicle_permit)) ? View.GONE : View.VISIBLE));
        ilStates.setVisibility((docType.equals(getString(R.string.vehicle_permit)) ? View.VISIBLE : View.GONE));
        if (docType.equals(getString(R.string.vehicle_registration))) {
            ilDescription.setHint(getString(R.string.vehicle_type));
        } else if (docType.equals(getString(R.string.vehicle_insurance))) {
            ilDescription.setHint(getString(R.string.name_on_document));
        } else if (docType.equals(getString(R.string.vehicle_permit))) {
            ilDescription.setHint(getString(R.string.vehicle_name));
        }
    }

    @OnClick(R.id.expired_layout)
    public void startDatePicker(View view) {
        DatePickerUtils.startDatePicker(this, this);
    }


    @OnClick(R.id.btn_continue)
    public void onClick(View view) {
        if (validateForm())
            Toast.makeText(this, etVehicleRegistrationNumber.getText() + " " + etNameOnDoc.getText() + " " + tvExpiredDate.getText(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "please enter all the details", Toast.LENGTH_SHORT).show();
    }

    private boolean validateForm() {
        if (etVehicleRegistrationNumber.getVisibility() == View.VISIBLE && TextValidationUtils.isEmpty(etVehicleRegistrationNumber.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.registration_number, this);
            return false;
        } else if (etNameOnDoc.getVisibility() == View.VISIBLE && TextValidationUtils.isEmpty(etNameOnDoc.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.name_on_document, this);
            return false;
        } else if (tvExpiredDate.getVisibility() == View.VISIBLE && TextValidationUtils.isEmpty(tvExpiredDate.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.expiry_date, this);
            return false;
        } else
            return true;

    }

    @Override
    public void setDateFromDatePicker(String selectedDate) {
        tvExpiredDate.setText(selectedDate);
    }
}
