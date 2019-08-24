package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.DatePickerCallBackInterface;
import com.example.cabway.viewModels.DocumentViewModel;
import com.example.core.CommonModels.DocumentModel;
import com.example.database.Utills.AppConstants;

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
    private boolean isFromLogin = false;
    private DocumentViewModel documentViewModel;
    private String mFilePath;
    private boolean isIssuedDatePicker = true;
    private DocumentModel document = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_uplooad);
        setUpActionBar();
        ButterKnife.bind(this);
        documentViewModel = ViewModelProviders.of(this).get(DocumentViewModel.class);
        documentViewModel.init();
        getExtras();
        setTitle(docType);
        setUiForDoc();
        setHints();
        if(document != null)
            setDocumentToUi();
        setDocumentUploadObserver();
    }

    private void setDocumentToUi(){
        etDocNumber.setText(document.getDocumentNumber());
        etNameOnDoc.setText(document.getNameOnDocument());
        etVehicleType.setText(document.getVehicleType());
        etStateName.setText(document.getStateName());
        etGstNumber.setText(document.getGstNumber());
        tvIssuedDate.setText(document.getIssueDate());
        tvExpireDate.setText(document.getExpiryDate());
    }

    private void getExtras(){
        docType = getIntent().getStringExtra(IntentConstants.DOC_TYPE_EXTRA);
        isFromLogin = getIntent().getBooleanExtra(IntentConstants.IS_FROM_LOGIN, false);
        document = (DocumentModel) getIntent().getSerializableExtra(IntentConstants.DOCUMENT_EXTRA);
    }

    private void setDocumentUploadObserver() {
        documentViewModel.getDocumentUploadResponseMld().observe(this, documentUploadResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(documentUploadResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(UploadDocumentActivity.this, documentUploadResponse.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UploadDocumentActivity.this, documentUploadResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setHints() {
        if (docType.equals(getString(R.string.vehicle_permit)))
            tilNameOnDoc.setHint(getString(R.string.vehicle_name));
        else if (docType.equals(getString(R.string.shop_act)))
            tilNameOnDoc.setHint(getString(R.string.name_of_shop));
    }

    private void setUiForDoc() {
        if (docType.equals(getString(R.string.shop_act))) {
            tilStateName.setVisibility(View.VISIBLE);
            tilGstNumber.setVisibility(View.VISIBLE);
            llIssuedDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.driver_license))) {
            tilVehicleType.setVisibility(View.VISIBLE);
            llIssuedDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_permit))) {
            tilStateName.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_registration))) {
            tilNameOnDoc.setVisibility(View.GONE);
            tilVehicleType.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.aadhar_card))) {
            llExpiryDate.setVisibility(View.GONE);
        }
//        else if (docType.equals(getString(R.string.vehicle_insurance))) {
//
//        }
    }

    @OnClick({R.id.tv_issued_date, R.id.tv_expired_date})
    public void startDatePicker(View view) {
        isIssuedDatePicker = (view.getId() == R.id.tv_issued_date);
        DatePickerUtils.startDatePicker(this, this);
    }


    @OnClick(R.id.btn_continue)
    public void onClick(View view) {
        if (checkNetworkAvailableWithoutError() && validate()) {
            showProgressDialog(AppConstants.PLEASE_WAIT, false);
            DocumentModel documentModel = getDocumentModel();
            documentViewModel.getDocumentRepository().uploadDocument(documentViewModel.getDocumentUploadResponseMld(), documentModel, mFilePath);
        }

    }

    private DocumentModel getDocumentModel() {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setDocumentNumber(etDocNumber.getText().toString());
        documentModel.setDocumentType(docType);
        if(isFromLogin)
            documentModel.setUuid(AppConstants.DEFAULT_ID);
        documentModel.setUserId(appPreferences.getUserDetails().uuId);
        if (tilNameOnDoc.getVisibility() == View.VISIBLE)
            documentModel.setNameOnDocument(etNameOnDoc.getText().toString());
        if (tilStateName.getVisibility() == View.VISIBLE)
            documentModel.setStateName(etStateName.getText().toString());
        if (tilGstNumber.getVisibility() == View.VISIBLE)
            documentModel.setGstNumber(etGstNumber.getText().toString());
        if (tilVehicleType.getVisibility() == View.VISIBLE)
            documentModel.setVehicleType(etVehicleType.getText().toString());
        if (llIssuedDate.getVisibility() == View.VISIBLE)
            documentModel.setIssueDate(tvIssuedDate.getText().toString());
        if (llExpiryDate.getVisibility() == View.VISIBLE)
            documentModel.setExpiryDate(tvExpireDate.getText().toString());

        return documentModel;
    }

    @Override
    public void setDateFromDatePicker(String selectedDate) {
        if (isIssuedDatePicker)
            tvIssuedDate.setText(selectedDate);
        else
            tvExpireDate.setText(selectedDate);
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
        } else if (llIssuedDate.getVisibility() == View.VISIBLE && llExpiryDate.getVisibility() == View.VISIBLE
                && (tvExpireDate.getText().toString().compareTo(tvIssuedDate.getText().toString()) < 0)) {
            Toast.makeText(this, getString(R.string.date_validation), Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    @OnClick(R.id.iv_document_image)
    void selectImage() {
        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted())
            ImageUtils.pickImage(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageUtils.IMAGE_PICK) {
            String fileName = "abc.png";
            String filePath = ImageUtils.onImagePickResult(requestCode, resultCode, data, fileName, this);
            if (!TextValidationUtils.isEmpty(filePath)) {
                mFilePath = filePath;
                Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void performActionAfterPermission() {
        ImageUtils.pickImage(this);
    }
}
