package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;
import static com.example.cabway.Utils.TextValidationUtils.showMandatoryErrorUsingString;

public class UploadDocumentActivity extends BaseActivity implements DatePickerCallBackInterface {

    @BindView(R.id.il_doc_registration_number)
    TextInputLayout tilCardNumber;
    @BindView(R.id.il_name_of_doc)
    TextInputLayout tilNameOnDoc;
    @BindView(R.id.il_vehicle_type)
    TextInputLayout tilVehicleType;
    @BindView(R.id.il_state_name)
    TextInputLayout tilStateName;
    @BindView(R.id.il_gst_number)
    TextInputLayout tilGstNumber;
    @BindView(R.id.ll_issued_date_layout)
    LinearLayout llIssuedDate;
    @BindView(R.id.ll_expired_date_layout)
    LinearLayout llExpiryDate;

    @BindView(R.id.et_doc_number)
    EditText etDocNumber;
    @BindView(R.id.et_name_on_doc)
    EditText etNameOnDoc;
    @BindView(R.id.et_vehicle_type)
    EditText etVehicleType;
    @BindView(R.id.et_state_name)
    EditText etStateName;
    @BindView(R.id.et_gst_number)
    EditText etGstNumber;
    @BindView(R.id.tv_issued_date)
    TextView tvIssuedDate;
    @BindView(R.id.tv_expired_date)
    TextView tvExpireDate;

    private String docType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doocument_uplooad);
        setUpActionBar();
        ButterKnife.bind(this);
        docType = getIntent().getStringExtra(IntentConstants.DOC_TYPE_EXTRA);
        setTitle(docType);
        setUiForDoc();
        setHints();
    }

    private void setHints() {
        if (docType.equals(getString(R.string.vehicle_permit)))
            tilNameOnDoc.setHint(getString(R.string.vehicle_name));
        else if (docType.equals(getString(R.string.shop_act)))
            tilNameOnDoc.setHint(getString(R.string.name_of_shop));
    }

    private void setUiForDoc() {
        if (docType.equals(getString(R.string.shop_act))) {
            tilNameOnDoc.setVisibility(View.VISIBLE);
            tilStateName.setVisibility(View.VISIBLE);
            tilGstNumber.setVisibility(View.VISIBLE);
            llIssuedDate.setVisibility(View.VISIBLE);
            llExpiryDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.driver_license))) {
            tilVehicleType.setVisibility(View.VISIBLE);
            llIssuedDate.setVisibility(View.VISIBLE);
            llExpiryDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_permit))) {
            tilNameOnDoc.setVisibility(View.VISIBLE);
            tilStateName.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_registration))) {
            tilVehicleType.setVisibility(View.VISIBLE);
            llExpiryDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_insurance))) {
            tilNameOnDoc.setVisibility(View.VISIBLE);
            llExpiryDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.aadhar_card))) {
            tilNameOnDoc.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_issued_date, R.id.tv_expired_date})
    public void startDatePicker(View view) {
        DatePickerUtils.startDatePicker(this, this);
    }


    @OnClick(R.id.btn_continue)
    public void onClick(View view) {
        if (validate()) {
            Toast.makeText(this, "sucess", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setDateFromDatePicker(String selectedDate) {

    }

    private boolean validate() {
        if (TextUtils.isEmpty(etDocNumber.getText().toString())) {
            showMandatoryError(R.string.registration_number, this);
            return false;
        } else if (tilNameOnDoc.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etNameOnDoc.getText().toString())) {
            showMandatoryErrorUsingString(Objects.requireNonNull(tilNameOnDoc.getHint()).toString(), this);
            return false;
        } else if (tilStateName.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etStateName.getText().toString())) {
            showMandatoryError(R.string.state_name, this);
            return false;
        } else if (tilGstNumber.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etGstNumber.getText().toString())) {
            showMandatoryError(R.string.gst_number, this);
            return false;
        } else if (tilVehicleType.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etVehicleType.getText().toString())) {
            showMandatoryError(R.string.vehicle_type, this);
            return false;
        } else if (llIssuedDate.getVisibility() == View.VISIBLE && TextUtils.isEmpty(tvIssuedDate.getText().toString())) {
            showMandatoryError(R.string.issued_on, this);
            return false;
        } else if (llExpiryDate.getVisibility() == View.VISIBLE && TextUtils.isEmpty(tvExpireDate.getText().toString())) {
            showMandatoryError(R.string.expiry_date, this);
            return false;
        } else
            return true;

    }
}
