package com.example.cabway.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.DialogUtils;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.SpinnerUtils;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.RegistrationInterface;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.cabway.ui.dialogs.DialogOtp;
import com.example.cabway.viewModels.UserViewModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;


public class RegistrationActivity extends BaseActivity implements RegistrationInterface, CitySpinnerAdapter.ItemSelectedCallback {

    @BindView(R.id.first_name)
    EditText etFirstName;

    @BindView(R.id.last_name)
    EditText etLastName;

    @BindView(R.id.til_agency_name)
    TextInputLayout tilAgencyName;

    @BindView(R.id.agency_name)
    EditText etAgencyName;

    @BindView(R.id.phone)
    EditText etPhone;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.sp_city)
    AppCompatSpinner spCity;

    @BindView(R.id.sp_state)
    AppCompatSpinner spState;

    @BindView(R.id.tv_state_hint)
    TextView tvStateHint;

    @BindView(R.id.tv_city_hint)
    TextView tvCityHint;

    @BindView(R.id.et_pincode)
    EditText etPincode;

    @BindView(R.id.password)
    EditText etPassword;

    @BindView(R.id.confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.generate_otp)
    Button bGenerateOtp;

    @BindView(R.id.submit)
    Button bSubmit;

    @BindView(R.id.type)
    RadioGroup type;

    @BindView(R.id.uploadImageLayout)
    FrameLayout uploadImageLayout;

    @BindView(R.id.uploadImage)
    ImageView ivProfileImage;

    String mFilePath = "";
    private DialogOtp dialogOtp;
    private String mMobileNumber;
    private UserViewModel userViewModel;
    private CityModel selectedCity;
    private boolean isRegistrationComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpActionBar();
        ButterKnife.bind(this);
        setUpUi();
        isRegistrationComplete = false;
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        dialogOtp = new DialogOtp(this);
        setObservers();
    }

    private void setUpUi() {
        spCity.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.CITY, 0, spCity));
        spState.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.STATE, 0, spState));
    }

    private void setObservers() {
        setOtpObserver();
        setVerifyOtpObserver();
        setRegisterUserObserver();
    }

    private void setOtpObserver() {
        userViewModel.getOtpResponseMld().observe(this, otpResponse -> {
            removeProgressDialog();
            if (isSuccessResponse(otpResponse)) {
                Toast.makeText(RegistrationActivity.this, R.string.otp_sent, Toast.LENGTH_SHORT).show();
                if (!dialogOtp.isShowing())
                    dialogOtp.showCustomDialogVerifyMobile(mMobileNumber);
            } else {
                Toast.makeText(RegistrationActivity.this, otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVerifyOtpObserver() {
        userViewModel.getVerifyOtpResponseMld().observe(this, verifyOtpResponse -> {
            removeProgressDialog();
            if (isSuccessResponse(verifyOtpResponse)) {
                dialogOtp.otpVerificationSuccess();
                setUiForOtpSuccess();
            } else
                dialogOtp.otpVerificationFailed();
        });
    }

    private void setRegisterUserObserver() {
        userViewModel.getUserRegistrationResponseMld().observe(this, userRegistrationResponse -> {
            removeProgressDialog();
            if (isSuccessResponse(userRegistrationResponse)) {
                Toast.makeText(RegistrationActivity.this, userRegistrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                isRegistrationComplete = true;
                onBackPressed();
            } else {
                Toast.makeText(RegistrationActivity.this, userRegistrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isRegistrationComplete)
            super.onBackPressed();
        else
            DialogUtils.showMessageDialog(this, getString(R.string.registration_exit_message), registrationCancelListener);
    }

    DialogInterface.OnClickListener registrationCancelListener = (dialog, id) -> {
        if (checkNetworkAvailableWithoutError()) {
           RegistrationActivity.super.onBackPressed();
        }

    };
    @OnClick(R.id.generate_otp)
    void generateOtp() {
        requestOtp();
    }

    @OnClick(R.id.submit)
    void submitInfo() {
        if (checkNetworkAvailableWithoutError()) {
            if (validateAllFields()) {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                UserModel userModel = createUserModel();
                userViewModel.getUserRepository().registerUser(userViewModel.getUserRegistrationResponseMld(), userModel, mFilePath);
            }
        }
    }

    @OnClick(R.id.uploadImageLayout)
    void selectImage() {
        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted())
            ImageUtils.pickImage(this);
//            showProgressDialog(AppConstants.PLEASE_WAIT, false);
    }

    private UserModel createUserModel() {
        UserModel userModel = new UserModel();
        userModel.firstName = etFirstName.getText().toString();
        userModel.lastName = etLastName.getText().toString();
        if (etAgencyName.getVisibility() == View.VISIBLE)
            userModel.agencyName = etAgencyName.getText().toString();
        userModel.password = etPassword.getText().toString();
        userModel.mobileNo = etPhone.getText().toString();
        userModel.address = etAddress.getText().toString();
        userModel.email = etEmail.getText().toString();
        userModel.cityCode = selectedCity;
        userModel.pinCode = etPincode.getText().toString();
        userModel.role = (type.getCheckedRadioButtonId() == R.id.agency) ? AppConstants.AGENCY : AppConstants.DRIVER;
        return userModel;
    }

    @Override
    public void verifyOtp(String enteredOtp) {
        if (checkNetworkAvailableWithoutError()) {
            showProgressDialog(AppConstants.PLEASE_WAIT, false);
            userViewModel.getUserRepository().verifyOtp(userViewModel.getVerifyOtpResponseMld(), mMobileNumber, Integer.parseInt(enteredOtp));
        }
    }

    @Override
    public void requestOtp() {
        mMobileNumber = etPhone.getText().toString().trim();
        if (TextValidationUtils.validateMobileNumber(mMobileNumber)) {
            if (checkNetworkAvailableWithoutError()) {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                userViewModel.getUserRepository().getOtp(userViewModel.getOtpResponseMld(), mMobileNumber);
            }
        } else
            Toast.makeText(this, R.string.mobile_length_message, Toast.LENGTH_SHORT).show();

    }

    private void setUiForOtpSuccess() {
        etFirstName.setVisibility(View.VISIBLE);
        etLastName.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        etConfirmPassword.setVisibility(View.VISIBLE);
        etEmail.setVisibility(View.VISIBLE);
        etAddress.setVisibility(View.VISIBLE);
        spCity.setVisibility(View.VISIBLE);
        spState.setVisibility(View.VISIBLE);
        tvCityHint.setVisibility(View.VISIBLE);
        tvStateHint.setVisibility(View.VISIBLE);
        etPincode.setVisibility(View.VISIBLE);
        bSubmit.setVisibility(View.VISIBLE);
        type.setVisibility(View.VISIBLE);
        etPhone.setInputType(InputType.TYPE_NULL);
        uploadImageLayout.setVisibility(View.VISIBLE);
        etPhone.setKeyListener(null);
        bGenerateOtp.setVisibility(View.GONE);
        if ((type.getCheckedRadioButtonId() == R.id.agency)) {
            etAgencyName.setVisibility(View.VISIBLE);
            tilAgencyName.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.agency, R.id.driver})
    void changeUi(View view) {
        etAgencyName.setVisibility((view.getId() == R.id.agency) ? View.VISIBLE : View.GONE);
        tilAgencyName.setVisibility((view.getId() == R.id.agency) ? View.VISIBLE : View.GONE);
    }

    private boolean validateAllFields() {
        if (TextValidationUtils.isEmpty(etFirstName.getText().toString())) {
            showMandatoryError(R.string.first_name, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etLastName.getText().toString())) {
            showMandatoryError(R.string.last_name, this);
            return false;
        } else if (etAgencyName.getVisibility() == View.VISIBLE && TextValidationUtils.isEmpty(etAgencyName.getText().toString())) {
            showMandatoryError(R.string.agency_name, this);
            return false;
        } else if (!TextValidationUtils.isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!TextValidationUtils.isValidAddress(etAddress.getText().toString(), this)) {
            return false;
        } else if (spCity.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.city, this);
            return false;
        } else if (spState.getSelectedItemPosition() == 0) {
            showMandatoryError(R.string.state, this);
            return false;
        } else if (TextValidationUtils.isValidPinCode(etPincode.getText().toString())) {
            showMandatoryError(R.string.pincode, this);
            return false;
        } else
            return TextValidationUtils.isValidPassword(etPassword.getText().toString(), etConfirmPassword.getText().toString(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageUtils.IMAGE_PICK) {
//            removeProgressDialog();
            String filePath = ImageUtils.onImagePickResult(requestCode, resultCode, data, this);
            if (!TextValidationUtils.isEmpty(filePath)) {
                mFilePath = filePath;
                Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
                ImageUtils.setImageFromFilePath(this, mFilePath, ivProfileImage);
            }
        }
    }

    @Override
    public void performActionAfterPermission() {
        ImageUtils.pickImage(this);
    }

    @Override
    public <T> void sendDataOnSelection(T data) {
        if (data instanceof StateModel) {
            spCity.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.CITY, ((StateModel) data).getId(), spCity));
        } else if (data instanceof CityModel) {
            selectedCity = (CityModel) data;
        }
    }

}
