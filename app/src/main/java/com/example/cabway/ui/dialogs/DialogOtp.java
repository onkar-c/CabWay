package com.example.cabway.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.AppConstants;
import com.example.cabway.ui.Interfaces.RegistrationInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DialogOtp {

    @BindView(R.id.otp)
    EditText mEdtOtp;

    @BindView(R.id.regenerateOtp)
    TextView mTxvResendOtp;

    @BindView(R.id.text_resend_code)
    TextView mTvTextResendCode;

    @BindView(R.id.verifyBtn)
    TextView mBtnSubmit;

    @BindView(R.id.text_heading)
    TextView mTvHeader;

    @BindView(R.id.crossBtn)
    TextView mCrossButton;

    private Dialog dialog;
    private Context mContext;
    private RegistrationInterface registrationInterface;

    public DialogOtp(Context context) {
        mContext = context;
        this.registrationInterface = (RegistrationInterface) context;
        @SuppressLint("InflateParams")
        View detailView = LayoutInflater.from(context).inflate(R.layout.dialog_verify_mobile, null, false);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(detailView);
        ButterKnife.bind(this, detailView);
        dialog.setCancelable(false);
    }

    public void showCustomDialogVerifyMobile(final String mobileNo) {

            String mobText = mContext.getString(R.string.otp_text);

            mTvHeader.setText(mobText.replace("*","******" + mobileNo.substring(6, 10)));
            dialog.show();
    }

    @OnClick(R.id.verifyBtn)
    void verifyOtp() {
        String otp = mEdtOtp.getText().toString();
        if(otp.length() < AppConstants.OTP_LENGTH)
            Toast.makeText(mContext,R.string.otp_length_message,Toast.LENGTH_SHORT).show();
        else
            registrationInterface.verifyOtp(otp);
    }

    @OnClick(R.id.regenerateOtp)
    void regenerateOtp() {
        registrationInterface.requestOtp();
    }

    @OnClick(R.id.crossBtn)
    void closeDialogue() {
        dialog.dismiss();
    }

    private void removeOtpDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void otpVerificationFailed(){
        Toast.makeText(mContext,R.string.otp_verification_failed_message,Toast.LENGTH_SHORT).show();
    }

    public void otpVerificationSuccess(){
        Toast.makeText(mContext,R.string.otp_verification_success_message,Toast.LENGTH_SHORT).show();
        removeOtpDialog();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }
}



