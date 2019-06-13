package com.example.cabway.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cabway.R;

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

    @BindView(R.id.tv_count_down)
    TextView mTvCountDown;

    @BindView(R.id.crossBtn)
    TextView mCrossButton;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBarTimer;

    private Context mContext;
    private Dialog dialog;


    private CountDownTimer mCountDownTimer;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mProgressBarTimer.setVisibility(View.GONE);
            mTxvResendOtp.setVisibility(View.VISIBLE);
        }
    };

    public DialogOtp(Context context) {
        mContext = context;
        @SuppressLint("InflateParams")
        View detailView = LayoutInflater.from(context).inflate(R.layout.dialog_verify_mobile, null, false);
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(detailView);
        ButterKnife.bind(this, detailView);
        dialog.setCancelable(false);
    }

    //after click on Verify button
    public void showCustomDialogVerifyMobile(final String mobileNo) {
        try {

            String mobText = "******" + mobileNo.substring(6, 10);
            String heading = mContext.getResources().getString(R.string.otp_text).replace("*", mobText);
            mTvHeader.setText(heading);
            mProgressBarTimer.setVisibility(View.VISIBLE);
            mTxvResendOtp.setVisibility(View.GONE);
            mTvTextResendCode.setText(mContext.getString(R.string.waiting_for_code));


            Handler mHandler = new Handler();

            mHandler.postDelayed(mRunnable, 30 * 1000);

            mCountDownTimer = new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    mTvCountDown.setText(String.format("%s", String.valueOf(millisUntilFinished / 1000)));
                }

                public void onFinish() {
                    mTvCountDown.setText("");
                    mProgressBarTimer.setVisibility(View.GONE);
                    mTxvResendOtp.setVisibility(View.VISIBLE);
                    mTvTextResendCode.setText(mContext.getString(R.string.not_received_your_code));
                }

            };
            mCountDownTimer.start();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.verifyBtn)
    void verifyOtp() {
        removeOtpDialog();
    }

    @OnClick(R.id.regenerateOtp)
    void regenerateOtp() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish();
        }
        resetTimer();
    }

    @OnClick(R.id.crossBtn)
    void closeDialogue() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish();
            mCountDownTimer = null;
        }
        dialog.dismiss();
    }


    private void removeOtpDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void resetTimer() {
        mProgressBarTimer.setVisibility(View.VISIBLE);
        mTxvResendOtp.setVisibility(View.GONE);
        mTvTextResendCode.setText(mContext.getString(R.string.waiting_for_code));
        if (mCountDownTimer != null)
            mCountDownTimer.start();


    }
}



