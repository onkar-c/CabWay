package com.example.cabway.ui.activities;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import com.example.cabway.Utils.SpinnerUtils;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.core.CommonModels.StateModel;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class UploadDocumentActivity extends BaseActivity implements DatePickerCallBackInterface, CitySpinnerAdapter.ItemSelectedCallback {

    @BindView(R.id.il_doc_registration_number)
    TextInputLayout tilCardNumber;
    @BindView(R.id.il_name_of_doc)
    TextInputLayout tilNameOnDoc;
    @BindView(R.id.il_vehicle_type)
    TextInputLayout tilVehicleType;
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
    @BindView(R.id.tv_state_hint)
    TextView tvStateHint;
    @BindView(R.id.sp_state)
    AppCompatSpinner spState;
    @BindView(R.id.et_gst_number)
    EditText etGstNumber;
    @BindView(R.id.tv_issued_date)
    TextView tvIssuedDate;
    @BindView(R.id.tv_expired_date)
    TextView tvExpireDate;
    @BindView(R.id.iv_document_image)
    ImageView ivDocumentImage;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    @BindView(R.id.iv_add_doc)
    ImageView ivAddDocument;

    private String docType;
    private boolean isFromLogin = false;
    private DocumentViewModel documentViewModel;
    private String mFilePath;
    private boolean isIssuedDatePicker = true;
    private DocumentModel document = null;
    private boolean isEditMode = false;

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
        if (document != null)
            setDocumentToUi();
        setDocumentUploadObserver();
    }

    private void setDocumentToUi() {
        toggleAllFields(false);
        etDocNumber.setText(document.getDocumentNumber());
        etNameOnDoc.setText(document.getNameOnDocument());
        etVehicleType.setText(document.getVehicleType());
        spState.setSelection(SpinnerUtils.getStatePosition(document.getStateName()));
        etGstNumber.setText(document.getGstNumber());
        tvIssuedDate.setText(document.getIssueDate());
        tvExpireDate.setText(document.getExpiryDate());
        if (document.getImageUrl() != null)
            ImageUtils.setImageFromUrl(this, document.getImageUrl(), ivDocumentImage);
    }

    private void getExtras() {
        docType = getIntent().getStringExtra(IntentConstants.DOC_TYPE_EXTRA);
        isFromLogin = getIntent().getBooleanExtra(IntentConstants.IS_FROM_LOGIN, false);
        document = (DocumentModel) getIntent().getSerializableExtra(IntentConstants.DOCUMENT_EXTRA);
    }

    private void setDocumentUploadObserver() {
        documentViewModel.getDocumentUploadResponseMld().observe(this, documentUploadResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(documentUploadResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(UploadDocumentActivity.this, documentUploadResponse.getMessage(), Toast.LENGTH_SHORT).show();
                onBackPressed();
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
            tvStateHint.setVisibility(View.VISIBLE);
            spState.setVisibility(View.VISIBLE);
            tilGstNumber.setVisibility(View.VISIBLE);
            llIssuedDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.driver_license))) {
            tilVehicleType.setVisibility(View.VISIBLE);
            llIssuedDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_permit))) {
            tvStateHint.setVisibility(View.VISIBLE);
            spState.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_registration))) {
            tilNameOnDoc.setVisibility(View.GONE);
            tilVehicleType.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.aadhar_card))) {
            llExpiryDate.setVisibility(View.GONE);
        } if(spState.getVisibility() == View.VISIBLE)
            spState.setAdapter(SpinnerUtils.setSpinnerAdapter(this,AppConstants.STATE,"0"));
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
        DocumentModel documentModel = getDocumentModel();
        if (checkNetworkAvailableWithoutError() && validate()) {
            if (validateDocWise(documentModel)) {
                if (isEditMode) {
                    btnContinue.setVisibility(View.GONE);
                    isEditMode = false;
                    toggleAllFields(false);
                }
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                documentViewModel.getDocumentRepository().uploadDocument(documentViewModel.getDocumentUploadResponseMld(), documentModel, mFilePath);
            }
        }
    }

    private DocumentModel getDocumentModel() {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setDocumentNumber(etDocNumber.getText().toString());
        documentModel.setDocumentType(docType);
        if (isFromLogin)
            documentModel.setUuid(AppConstants.DEFAULT_ID);
        documentModel.setUserId(appPreferences.getUserDetails().uuId);
        if (tilNameOnDoc.getVisibility() == View.VISIBLE)
            documentModel.setNameOnDocument(etNameOnDoc.getText().toString());
        if (spState.getVisibility() == View.VISIBLE)
            documentModel.setStateName(SpinnerUtils.getStateData(spState.getSelectedItemId()).getName());
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
        } else if (spState.getVisibility() == View.VISIBLE && spState.getSelectedItemPosition()!=0) {
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


    private boolean validateDocWise(DocumentModel document){
       if(docType.equals(getResources().getString(R.string.aadhar_card))) {
           return TextValidationUtils.validateAdharCard(this, document);
       }else  if(docType.equals(getResources().getString(R.string.driver_license))) {
           return TextValidationUtils.validateDrivingLicense(this, document);
       } else if(docType.equals(getResources().getString(R.string.vehicle_registration))) {
            return TextValidationUtils.validateVehicleRegistration(this, document);
        }else  if(docType.equals(getResources().getString(R.string.vehicle_insurance))) {
           return TextValidationUtils.validateVehicleInsurance(this, document);
       } if(docType.equals(getResources().getString(R.string.vehicle_permit))) {
            return TextValidationUtils.validateVehiclePermit(this, document);
       }
       return true;
    }


    @OnClick({R.id.iv_document_image, R.id.iv_add_doc})
    void selectImage() {
        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted())
            ImageUtils.pickImage(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageUtils.IMAGE_PICK) {
            String fileName = "abc.png";
            String filePath = ImageUtils.onImagePickResult(requestCode, resultCode, data, fileName, this);
            if (!TextValidationUtils.isEmpty(filePath)) {
                mFilePath = filePath;
                Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
                if (mFilePath != null)
                    ImageUtils.setImageFromFilePath(this, mFilePath, ivDocumentImage);
            }
        }
    }

    @Override
    public void performActionAfterPermission() {
        ImageUtils.pickImage(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (document != null) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_edit, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu.findItem(R.id.action_edit) != null)
        menu.findItem(R.id.action_edit).setVisible(!isEditMode);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            isEditMode = true;
            toggleAllFields(true);
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleAllFields(boolean isEnabled) {
        etDocNumber.setEnabled(isEnabled);
        etNameOnDoc.setEnabled(isEnabled);
        etVehicleType.setEnabled(isEnabled);
        spState.setEnabled(isEnabled);
        etGstNumber.setEnabled(isEnabled);
        tvIssuedDate.setEnabled(isEnabled);
        tvExpireDate.setEnabled(isEnabled);
        ivDocumentImage.setClickable(isEnabled);
        ivAddDocument.setClickable(isEnabled);
        btnContinue.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        invalidateOptionsMenu();
    }

    @Override
    public <T> void sendDataOnSelection(T data) {
        if(data instanceof StateModel){
        }
    }
}
