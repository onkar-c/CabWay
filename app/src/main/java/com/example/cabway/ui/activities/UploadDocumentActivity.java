package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.CarTypeSpinnerUtils;
import com.example.cabway.Utils.DateTimeUtils;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.Utils.SpinnerUtils;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.DateTimePickerCallBackInterface;
import com.example.cabway.ui.adapter.CarTypeSpinnerAdapter;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.cabway.viewModels.DocumentViewModel;
import com.example.core.CommonModels.DocumentModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.CommonModels.VehicleTypeModel;
import com.example.database.Utills.AppConstants;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;
import static com.example.cabway.Utils.TextValidationUtils.showMandatoryErrorUsingString;

public class UploadDocumentActivity extends BaseActivity implements DateTimePickerCallBackInterface, CitySpinnerAdapter.ItemSelectedCallback, CarTypeSpinnerAdapter.TypeSelectedCallback {

    @BindView(R.id.il_doc_registration_number)
    TextInputLayout tilCardNumber;
    @BindView(R.id.il_name_of_doc)
    TextInputLayout tilNameOnDoc;
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
    @BindView(R.id.tv_vehicle_type_hint)
    TextView tvVehicleTypeHint;
    @BindView(R.id.sp_vehicle_type)
    AppCompatSpinner spVehicleType;
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
    @BindView(R.id.tv_upload_img)
    TextView tvUploadImg;

    private String docType;
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
        if (document.getVehicleType() != null)
            spVehicleType.setSelection(CarTypeSpinnerUtils.getVehicleTypePosition(document.getVehicleType()));
        if (!TextValidationUtils.isEmpty(document.getStateName()))
            spState.setSelection(SpinnerUtils.getStatePositionByName(document.getStateName()));
        etGstNumber.setText(document.getGstNumber());
        tvIssuedDate.setText(document.getIssueDate());
        tvExpireDate.setText(document.getExpiryDate());
        ImageUtils.setImageFromUrl(this, document.getImageUrl(), ivDocumentImage, R.drawable.id_image);
    }

    private void getExtras() {
        docType = getIntent().getStringExtra(IntentConstants.DOC_TYPE_EXTRA);
        document = (DocumentModel) getIntent().getSerializableExtra(IntentConstants.DOCUMENT_EXTRA);
    }

    private void setDocumentUploadObserver() {
        documentViewModel.getDocumentUploadResponseMld().observe(this, documentUploadResponse -> {
            removeProgressDialog();
            if (isSuccessResponse(documentUploadResponse)) {
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
            tvVehicleTypeHint.setVisibility(View.VISIBLE);
            spVehicleType.setVisibility(View.VISIBLE);
            llIssuedDate.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_permit))) {
            tvStateHint.setVisibility(View.VISIBLE);
            spState.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.vehicle_registration))) {
            tilNameOnDoc.setVisibility(View.GONE);
            tvVehicleTypeHint.setVisibility(View.VISIBLE);
            spVehicleType.setVisibility(View.VISIBLE);
        } else if (docType.equals(getString(R.string.aadhar_card))) {
            llExpiryDate.setVisibility(View.GONE);
        }
        if (spState.getVisibility() == View.VISIBLE)
            spState.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.STATE, 0, spState));
        if (spVehicleType.getVisibility() == View.VISIBLE)
            spVehicleType.setAdapter(CarTypeSpinnerUtils.setSpinnerAdapter(this, spVehicleType));
//        else if (docType.equals(getString(R.string.vehicle_insurance))) {
//
//        }
    }

    @OnClick({R.id.tv_issued_date, R.id.tv_expired_date})
    public void startDatePicker(View view) {
        isIssuedDatePicker = (view.getId() == R.id.tv_issued_date);
        DateTimeUtils.startDatePicker(this, this, Calendar.getInstance(), isIssuedDatePicker);
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
        documentModel.setUuid((document == null) ? AppConstants.DEFAULT_ID : document.getUuid());
        documentModel.setUserId(appPreferences.getUserDetails().uuId);
        if (tilNameOnDoc.getVisibility() == View.VISIBLE) {
            documentModel.setNameOnDocument(etNameOnDoc.getText().toString());
        }
        if (spState.getVisibility() == View.VISIBLE)
            documentModel.setStateName(((StateModel) spState.getSelectedItem()).getName());
        if (tilGstNumber.getVisibility() == View.VISIBLE)
            documentModel.setGstNumber(etGstNumber.getText().toString());
        if (spVehicleType.getVisibility() == View.VISIBLE)
            documentModel.setVehicleType(((VehicleTypeModel) spVehicleType.getSelectedItem()).getType());
        if (llIssuedDate.getVisibility() == View.VISIBLE)
            documentModel.setIssueDate(tvIssuedDate.getText().toString());
        if (llExpiryDate.getVisibility() == View.VISIBLE)
            documentModel.setExpiryDate(tvExpireDate.getText().toString());

        return documentModel;
    }

    @Override
    public void setDateTimeFromDatePicker(String selectedDateTime) {
        if (isIssuedDatePicker)
            tvIssuedDate.setText(selectedDateTime);
        else
            tvExpireDate.setText(selectedDateTime);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etDocNumber.getText().toString())) {
            showMandatoryError(R.string.registration_number, this);
            return false;
        } else if (tilNameOnDoc.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etNameOnDoc.getText().toString())) {
            showMandatoryErrorUsingString(Objects.requireNonNull(tilNameOnDoc.getHint()).toString(), this);
            return false;
        } else if (spState.getVisibility() == View.VISIBLE && spState.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.state_name, this);
            return false;
        } else if (tilGstNumber.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etGstNumber.getText().toString())) {
            showMandatoryError(R.string.gst_number, this);
            return false;
        } else if (spVehicleType.getVisibility() == View.VISIBLE && spVehicleType.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.vehicle_type, this);
            return false;
        } else if (llIssuedDate.getVisibility() == View.VISIBLE && TextUtils.isEmpty(tvIssuedDate.getText().toString())) {
            showMandatoryError(R.string.error_issued_on, this);
            return false;
        } else if (llExpiryDate.getVisibility() == View.VISIBLE && TextUtils.isEmpty(tvExpireDate.getText().toString())) {
            showMandatoryError(R.string.error_expiry_date, this);
            return false;
        } else if (llIssuedDate.getVisibility() == View.VISIBLE && llExpiryDate.getVisibility() == View.VISIBLE
                && (tvExpireDate.getText().toString().compareTo(tvIssuedDate.getText().toString()) < 0)) {
            Toast.makeText(this, getString(R.string.date_validation), Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private boolean validateDocWise(DocumentModel document) {
        if (docType.equals(getResources().getString(R.string.aadhar_card))) {
            return TextValidationUtils.validateAdharCard(this, document);
        } else if (docType.equals(getResources().getString(R.string.driver_license))) {
            return TextValidationUtils.validateDrivingLicense(this, document);
        } else if (docType.equals(getResources().getString(R.string.vehicle_registration))) {
            return TextValidationUtils.validateVehicleRegistration(this, document);
        } else if (docType.equals(getResources().getString(R.string.vehicle_insurance))) {
            return TextValidationUtils.validateVehicleInsurance(this, document);
        }
        if (docType.equals(getResources().getString(R.string.vehicle_permit))) {
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
            String filePath = ImageUtils.onImagePickResult(requestCode, resultCode, data, this);
            if (!TextValidationUtils.isEmpty(filePath)) {
                mFilePath = filePath;
                //Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
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
        if (menu.findItem(R.id.action_edit) != null)
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
        spVehicleType.setEnabled(isEnabled);
        spState.setEnabled(isEnabled);
        etGstNumber.setEnabled(isEnabled);
        tvIssuedDate.setEnabled(isEnabled);
        tvExpireDate.setEnabled(isEnabled);
        ivDocumentImage.setClickable(isEnabled);
        ivAddDocument.setClickable(isEnabled);
        ivAddDocument.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        tvUploadImg.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        btnContinue.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        invalidateOptionsMenu();
    }

    @Override
    public void sendTypeOnSelection(VehicleTypeModel data) {
        Log.e("Testing", "Testing");
    }

    @Override
    public <T> void sendDataOnSelection(T data) {

    }
}
