package com.example.core.responseModel;

import com.example.core.CommonModels.CityModel;

import java.io.Serializable;

public class AgencyResponseModel implements Serializable {

    private int uuId;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String address;
    private String email;
    private String pinCode;
    private CityModel cityCode;
    private String profileImage;

    public int getUuId() {
        return uuId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPinCode() {
        return pinCode;
    }

    public CityModel getCityCode() {
        return cityCode;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
