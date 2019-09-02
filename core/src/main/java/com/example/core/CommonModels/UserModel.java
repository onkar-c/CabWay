package com.example.core.CommonModels;

import java.io.Serializable;

public class UserModel implements Serializable {
    public int uuId = 0;
    public String firstName;
    public String lastName;
    public String mobileNo;
    public String password;
    public String authKey;
    public String address;
    public String email;
    public String role;
    public String cityCode;
    public String pinCode;
    public CityModel cityPreferences;
    public boolean documentCompleted;
    public String profileImage;
}
