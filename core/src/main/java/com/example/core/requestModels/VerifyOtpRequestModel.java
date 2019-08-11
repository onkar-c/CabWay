package com.example.core.requestModels;

import com.google.gson.annotations.SerializedName;

public class VerifyOtpRequestModel {

    /**
     * mobileNo : string
     * otp : 0
     * otpType : Reset
     */

    @SerializedName("mobileNo")
    private String mobileNo;
    @SerializedName("otp")
    private int otp;
    @SerializedName("otpType")
    private String otpType;


    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    public void setOtp(int otp) {
        this.otp = otp;
    }


    public void setOtpType(String otpType) {
        this.otpType = otpType;
    }
}
