package com.example.core.requestModels;

import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {

    @SerializedName("mobileNo")
    private String mobileNo;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
