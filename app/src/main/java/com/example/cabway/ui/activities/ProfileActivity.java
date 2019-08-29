package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.cabway.viewModels.UserViewModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;
import static com.example.cabway.ui.activities.RegistrationActivity.getCities;
import static com.example.cabway.ui.activities.RegistrationActivity.getStates;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.addImage)
    ImageView ivAddProfile;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_role)
    EditText etRole;

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.sp_state)
    AppCompatSpinner spState;

    @BindView(R.id.sp_city)
    AppCompatSpinner spCity;

    @BindView(R.id.et_pincode)
    EditText etPincode;

    @BindView(R.id.btn_save)
    Button btnSave;
    String mFilePath = "";
    private List<CityModel> cityList, stateList;
    private UserViewModel userViewModel;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpActionBar();
        ButterKnife.bind(this);
        cityList = getCities();
        stateList = getStates();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        setUpdateUserObserver();
        setUpUi();
    }

    private void setUpUi() {

        UserModel user = appPreferences.getUserDetails();

        etName.setText(String.format("%s %s", user.firstName, user.lastName));
        etPhoneNumber.setText(user.mobileNo);
        etEmail.setText(user.email);
        etAddress.setText(user.address);
        etPincode.setText(user.pinCode);
        etRole.setText(user.role);
        CitySpinnerAdapter citySpinnerAdapter = new CitySpinnerAdapter(this, cityList);
        spCity.setAdapter(citySpinnerAdapter);
        spCity.setSelection(getSelectedCity(user.cityCode));
        CitySpinnerAdapter stateSpinnerAdapter = new CitySpinnerAdapter(this, stateList);
        spState.setAdapter(stateSpinnerAdapter);
        toggleUi(false);
    }

    private void setUpdateUserObserver() {
        userViewModel.getUserUpdateResponseMld().observe(this, userUpdateUserResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(userUpdateUserResponse).getStatus().equals(AppConstants.SUCCESS)) {
                toggleUi(false);
                if(userUpdateUserResponse.getUser() != null)
                appPreferences.setUserDetails(userUpdateUserResponse.getUser());
                Toast.makeText(ProfileActivity.this, userUpdateUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, userUpdateUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getSelectedCity(String cityCode) {
        for (int i = 0; i < cityList.size(); i++) {
            CityModel city = cityList.get(i);
            if (city.getCode().equals(cityCode)) {
                return i;
            }
        }
        return 0;
    }

    @OnClick(R.id.btn_save)
    public void save() {
        if (checkNetworkAvailableWithoutError()) {
            if (validateAllFields()) {
                showProgressDialog(AppConstants.PLEASE_WAIT, false);
                UserModel userModel = createUserModel();
                userViewModel.getUserRepository().updateUser(userViewModel.getUserUpdateResponseMld(), userModel, mFilePath);
            }
        }
    }

    private UserModel createUserModel() {
        UserModel userModel = appPreferences.getUserDetails();
        userModel.firstName = etName.getText().toString();
        userModel.lastName = etName.getText().toString();
        userModel.address = etAddress.getText().toString();
        userModel.email = etEmail.getText().toString();
        userModel.cityCode = getCities().get(spCity.getSelectedItemPosition()).getName();
        userModel.pinCode = etPincode.getText().toString();
        return userModel;
    }

    @OnClick({R.id.iv_profile, R.id.addImage})
    void selectImage() {
        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted())
            ImageUtils.pickImage(this);
    }

    private boolean validateAllFields() {
        if (TextValidationUtils.isEmpty(etName.getText().toString())) {
            showMandatoryError(R.string.full_name, this);
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
            return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageUtils.IMAGE_PICK) {
            String fileName = Math.random() + ".png";
            String filePath = ImageUtils.onImagePickResult(requestCode, resultCode, data, fileName, this);
            if (!TextValidationUtils.isEmpty(filePath)) {
                mFilePath = filePath;
                Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
                ImageUtils.setImageFromFilePath(this, mFilePath, ivProfile);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu.findItem(R.id.action_edit) != null)
        menu.findItem(R.id.action_edit).setVisible(!isEdit);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            toggleUi(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleUi(boolean isEnabled) {
        isEdit = isEnabled;
        etName.setFocusable(isEnabled);
        etName.setEnabled(isEnabled);
        etEmail.setEnabled(isEnabled);
        etAddress.setEnabled(isEnabled);
        etPincode.setEnabled(isEnabled);
        spState.setEnabled(isEnabled);
        spCity.setEnabled(isEnabled);
        ivAddProfile.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        btnSave.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        invalidateOptionsMenu();
    }
}
