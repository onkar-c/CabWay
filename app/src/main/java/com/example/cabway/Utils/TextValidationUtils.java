package com.example.cabway.Utils;

public class TextValidationUtils {

    public static boolean isEmpty(String stringToCheck){
        return (stringToCheck == null || stringToCheck.trim().length() == 0);
    }

    public static boolean validateMobileNumber(String mobileNumber){
            return mobileNumber.length() == 10;
    }
}
