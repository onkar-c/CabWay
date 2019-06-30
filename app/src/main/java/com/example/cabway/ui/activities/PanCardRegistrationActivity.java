package com.example.cabway.ui.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.TextValidationUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PanCardRegistrationActivity extends BaseActivity {

    @BindView(R.id.issued_date)
    TextView tvIssuedDate;
    @BindView(R.id.expired_date)
    TextView tvExpiredDate;
    @BindView(R.id.et_pan_number)
    EditText etPanNumber;
    @BindView(R.id.et_pan_name)
    EditText etPanName;

    private boolean isIssuedDate = false;
    private DatePickerDialog.OnDateSetListener myDateListener = (arg0, arg1, arg2, arg3) -> {
        String selectedDate = arg3 + "/" + arg2 + 1 + "/" + arg1;
        if (isIssuedDate)
            tvIssuedDate.setText(selectedDate);
        else
            tvExpiredDate.setText(selectedDate);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_card_registration);
        setUpActionBar();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.issued_layout, R.id.expired_layout})
    public void startDatePicker(View view) {
        switch (view.getId()) {
            case R.id.issued_layout:
                isIssuedDate = true;
                break;
            case R.id.expired_layout:
                isIssuedDate = false;
                break;
        }
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.btn_continue)
    public void onClick(View view) {
        if (validateForm())
            Toast.makeText(this, etPanNumber.getText() + " " + etPanName.getText() + " " + tvIssuedDate.getText() + " " + tvExpiredDate.getText(), Toast.LENGTH_SHORT).show();
    }

    private boolean validateForm() {
        if (TextValidationUtils.isEmpty(etPanNumber.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.pan_card_number, this);
            return false;
        } else if (TextValidationUtils.isEmpty(etPanName.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.pan_card_name, this);
            return false;
        } else if (TextValidationUtils.isEmpty(tvIssuedDate.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.issued_on, this);
            return false;
        } else if (TextValidationUtils.isEmpty(tvExpiredDate.getText().toString())) {
            TextValidationUtils.showMandatoryError(R.string.expiry_date, this);
            return false;
        } else
            return true;

    }

}
