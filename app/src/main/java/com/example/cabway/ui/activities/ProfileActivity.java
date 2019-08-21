package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.TextValidationUtils;
import com.example.cabway.ui.adapter.CitySpinnerAdapter;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.UserModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cabway.Utils.TextValidationUtils.showMandatoryError;
import static com.example.cabway.ui.activities.RegistrationActivity.getCities;
import static com.example.cabway.ui.activities.RegistrationActivity.getStates;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.iv_edit)
    ImageView ivEdit;

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

    private List<CityModel> cityList,stateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpActionBar();
        ButterKnife.bind(this);
        setUpUi();
    }

    private void setUpUi(){

        UserModel user = appPreferences.getUserDetails();

        etName.setText(String.format("%s %s", user.firstName, user.lastName));
        etPhoneNumber.setText(user.mobileNo);
        etEmail.setText(user.email);
        etAddress.setText(user.address);
        etPincode.setText(user.pinCode);
        etRole.setText(user.role);

        spCity.setEnabled(false);
        cityList=getCities();
        CitySpinnerAdapter citySpinnerAdapter=new CitySpinnerAdapter(this,cityList);
        spCity.setAdapter(citySpinnerAdapter);
        spCity.setSelection(getSelectedCity(user.cityCode));

        spState.setEnabled(false);
        stateList=getStates();
        CitySpinnerAdapter stateSpinnerAdapter=new CitySpinnerAdapter(this,stateList);
        spState.setAdapter(stateSpinnerAdapter);

    }

    private int getSelectedCity(String cityCode) {
        for(int i=0;i<cityList.size();i++){
            CityModel city=cityList.get(i);
            if(city.getCode().equals(cityCode)){
                return i;
            }
        }
        return 0;
    }

    @OnClick(R.id.iv_edit)
    public void onClickOfEdit(){
        etName.setFocusable(true);
        etName.setEnabled(true);
        etEmail.setEnabled(true);
        etPhoneNumber.setEnabled(true);
        etAddress.setEnabled(true);
        etPincode.setEnabled(true);
        spState.setEnabled(true);
        spCity.setEnabled(true);
        btnSave.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_save)
    public void save(){
        if (checkNetworkAvailableWithoutError()) {
            if (validateAllFields()) {

                etName.setFocusable(false);
                etName.setEnabled(false);
                etEmail.setEnabled(false);
                etPhoneNumber.setEnabled(false);
                etAddress.setEnabled(false);
                etPincode.setEnabled(false);
                spState.setEnabled(false);
                spCity.setEnabled(false);
                btnSave.setVisibility(View.GONE);
            }
        }

    }

    private boolean validateAllFields() {
        if (TextValidationUtils.isEmpty(etName.getText().toString())) {
            showMandatoryError(R.string.full_name, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etEmail.getText().toString())) {
            showMandatoryError(R.string.email, this);
            return false;
        }else if (!TextValidationUtils.isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextValidationUtils.isEmpty(etAddress.getText().toString())) {
            showMandatoryError(R.string.address, this);
            return false;
        }else if (spCity.getSelectedItemPosition()==0) {
            showMandatoryError(R.string.city, this);
            return false;
        }else if (spState.getSelectedItemPosition()==0) {
            showMandatoryError(R.string.state, this);
            return false;
        } else if (TextValidationUtils.isValidPinCode(etPincode.getText().toString())) {
            showMandatoryError(R.string.pincode, this);
            return false;
        } else
            return true;
    }
}
