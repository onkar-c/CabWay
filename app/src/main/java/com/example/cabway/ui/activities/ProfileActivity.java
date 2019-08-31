package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import com.example.cabway.R;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.SpinnerUtils;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.cabway.viewModels.UserViewModel;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.StateModel;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;

public class ProfileActivity extends BaseActivity implements CitySpinnerAdapter.ItemSelectedCallback {

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.addImage)
    ImageView ivAddProfile;

    @BindView(R.id.et_fname)
    EditText etFName;
    @BindView(R.id.et_lname)
    EditText etLName;

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
    private UserViewModel userViewModel;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpActionBar();
        ButterKnife.bind(this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        setUpdateUserObserver();
        setUpUi();
    }

    private void setUpUi() {

        UserModel user = appPreferences.getUserDetails();

        etFName.setText(user.firstName);
        etLName.setText(user.lastName);
        etPhoneNumber.setText(user.mobileNo);
        etEmail.setText(user.email);
        etAddress.setText(user.address);
        etPincode.setText(user.pinCode);
        etRole.setText(user.role);
        spCity.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.CITY, "0"));
        spCity.setSelection(SpinnerUtils.getCityPosition(user.cityCode));
        spState.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.STATE, "0"));
        //TODO:spState.setSelection(SpinnerUtils.getStateData(user.));
        toggleUi(false);
    }

    private void setUpdateUserObserver() {
        userViewModel.getUserUpdateResponseMld().observe(this, userUpdateUserResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(userUpdateUserResponse).getStatus().equals(AppConstants.SUCCESS)) {
                toggleUi(false);
                if (userUpdateUserResponse.getUser() != null)
                    appPreferences.setUserDetails(userUpdateUserResponse.getUser());
                Toast.makeText(ProfileActivity.this, userUpdateUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, userUpdateUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        userModel.firstName = etFName.getText().toString();
        userModel.lastName = etLName.getText().toString();
        userModel.address = etAddress.getText().toString();
        userModel.email = etEmail.getText().toString();
        userModel.cityCode = SpinnerUtils.getCityData(spCity.getSelectedItemPosition()).getCode();
        userModel.pinCode = etPincode.getText().toString();
        return userModel;
    }

    @OnClick({R.id.iv_profile, R.id.addImage})
    void selectImage() {
        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted())
            ImageUtils.pickImage(this);
    }

    private boolean validateAllFields() {
        if (TextValidationUtils.isEmpty(etFName.getText().toString())) {
            showMandatoryError(R.string.first_name, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etLName.getText().toString())) {
            showMandatoryError(R.string.last_name, this);
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
        super.onActivityResult(requestCode, resultCode, data);
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
        if (menu.findItem(R.id.action_edit) != null)
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
        etFName.setFocusable(isEnabled);
        etFName.setEnabled(isEnabled);
        etLName.setEnabled(isEnabled);
        etEmail.setEnabled(isEnabled);
        etAddress.setEnabled(isEnabled);
        etPincode.setEnabled(isEnabled);
        spState.setEnabled(isEnabled);
        spCity.setEnabled(isEnabled);
        ivAddProfile.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        btnSave.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        invalidateOptionsMenu();
    }

    @Override
    public <T> void sendDataOnSelection(T data) {
        if (data instanceof StateModel) {
            spCity.setAdapter(SpinnerUtils.setSpinnerAdapter(this, AppConstants.CITY, ((StateModel) data).getId()));
        } else if (data instanceof CityModel) {
        }
    }
}
