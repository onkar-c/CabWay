package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.Interfaces.RegistrationInterface;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.cabway.ui.dialogs.DialogOtp;
import com.example.cabway.viewModels.RegistrationViewModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;


public class RegistrationActivity extends BaseActivity implements RegistrationInterface {

    @BindView(R.id.first_name)
    EditText etFirstName;

    @BindView(R.id.last_name)
    EditText etLastName;

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
    private RegistrationViewModel registrationViewModel;

    public static List<CityModel> getCities() {
        CityModel city0 = new CityModel("0", "Select City");
        CityModel city1 = new CityModel("1", "Pune");
        CityModel city2 = new CityModel("2", "Mumbai");
        CityModel city3 = new CityModel("3", "Satara");
        CityModel city4 = new CityModel("4", "Banglore");

        List<CityModel> cityList = new ArrayList<>();
        cityList.add(city0);
        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);
        cityList.add(city4);

        return cityList;
    }

    public static List<CityModel> getStates() {
        CityModel city0 = new CityModel("0", "Select State");
        CityModel city1 = new CityModel("1", "Maharashtra");
        CityModel city2 = new CityModel("2", "Karnatak");
        CityModel city3 = new CityModel("3", "Rajastan");
        CityModel city4 = new CityModel("4", "Tamilnadu");

        List<CityModel> cityList = new ArrayList<>();
        cityList.add(city0);
        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);
        cityList.add(city4);

        return cityList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpActionBar();
        ButterKnife.bind(this);
        setUpUi();
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        registrationViewModel.init();
        dialogOtp = new DialogOtp(this);
        setObservers();
    }

    private void setUpUi() {
        CitySpinnerAdapter citySpinnerAdapter = new CitySpinnerAdapter(this, getCities());
        spCity.setAdapter(citySpinnerAdapter);

        CitySpinnerAdapter stateSpinnerAdapter = new CitySpinnerAdapter(this, getStates());
        spState.setAdapter(stateSpinnerAdapter);
    }

    private void setObservers() {
        setOtpObserver();
        setVerifyOtpObserver();
        setRegisterUserObserver();
    }

    private void setOtpObserver() {
        registrationViewModel.getOtpResponseMld().observe(this, otpResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(otpResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(RegistrationActivity.this, R.string.otp_sent, Toast.LENGTH_SHORT).show();
                if (!dialogOtp.isShowing())
                    dialogOtp.showCustomDialogVerifyMobile(mMobileNumber);
            } else {
                Toast.makeText(RegistrationActivity.this, otpResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVerifyOtpObserver() {
        registrationViewModel.getVerifyOtpResponseMld().observe(this, verifyOtpResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(verifyOtpResponse).getStatus().equals(AppConstants.SUCCESS)) {
                dialogOtp.otpVerificationSuccess();
                setUiForOtpSuccess();
            } else
                dialogOtp.otpVerificationFailed();
        });
    }

    private void setRegisterUserObserver() {
        registrationViewModel.getUserRegistrationResponseMld().observe(this, userRegistrationResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(userRegistrationResponse).getStatus().equals(AppConstants.SUCCESS)) {
                Toast.makeText(RegistrationActivity.this, userRegistrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(RegistrationActivity.this, userRegistrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

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
                registrationViewModel.getRegistrationRepository().registerUser(registrationViewModel.getUserRegistrationResponseMld(), userModel, mFilePath);
            }
        }
    }

    @OnClick({R.id.uploadImage, R.id.addImage})
    void selectImage() {
        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted())
            ImageUtils.pickImage(this);
//            showProgressDialog(AppConstants.PLEASE_WAIT, false);
    }

    private UserModel createUserModel() {
        UserModel userModel = new UserModel();
        userModel.firstName = etFirstName.getText().toString();
        userModel.lastName = etLastName.getText().toString();
        userModel.password = etPassword.getText().toString();
        userModel.mobileNo = etPhone.getText().toString();
        userModel.address = etAddress.getText().toString();
        userModel.email = etEmail.getText().toString();
        userModel.cityCode = getCities().get(spCity.getSelectedItemPosition()).getName();
        userModel.pinCode = etPincode.getText().toString();
        userModel.role = (type.getCheckedRadioButtonId() == R.id.agency) ? AppConstants.AGENCY : AppConstants.DRIVER;
        return userModel;
    }

    @Override
    public void verifyOtp(String enteredOtp) {
        if (checkNetworkAvailableWithoutError()) {
            showProgressDialog(AppConstants.PLEASE_WAIT, false);
            registrationViewModel.getRegistrationRepository().verifyOtp(registrationViewModel.getVerifyOtpResponseMld(), mMobileNumber, Integer.parseInt(enteredOtp));
        }
    }

    @Override
    public void requestOtp() {
        mMobileNumber = etPhone.getText().toString().trim();
        if (TextValidationUtils.validateMobileNumber(mMobileNumber)) {
            if (checkNetworkAvailableWithoutError()) {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                registrationViewModel.getRegistrationRepository().getOtp(registrationViewModel.getOtpResponseMld(), mMobileNumber);
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
    }

    private boolean validateAllFields() {
        if (TextValidationUtils.isEmpty(etFirstName.getText().toString())) {
            showMandatoryError(R.string.first_name, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etLastName.getText().toString())) {
            showMandatoryError(R.string.last_name, this);
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
        if (requestCode == ImageUtils.IMAGE_PICK) {
//            removeProgressDialog();
            String fileName = "abc.png";
            String filePath = ImageUtils.onImagePickResult(requestCode, resultCode, data, fileName, this);
            if (!TextValidationUtils.isEmpty(filePath)) {
                mFilePath = filePath;
                Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();

                Picasso.with(this)
                        .load(new File(mFilePath))
                        .into(ivProfileImage);
            }
        }
    }

    @Override
    public void performActionAfterPermission() {
        ImageUtils.pickImage(this);
    }

}
