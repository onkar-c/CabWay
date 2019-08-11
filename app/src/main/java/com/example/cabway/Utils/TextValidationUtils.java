package com.example.cabway.Utils;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import com.example.cabway.R;

public class TextValidationUtils {

    public static boolean isEmpty(String stringToCheck) {
        return (stringToCheck == null || stringToCheck.trim().length() == 0);
    }

    public static boolean validateMobileNumber(String mobileNumber) {
        return mobileNumber.length() == 10;
    }

    public static boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void showMandatoryError(int field, Context context) {
        String message = String.format(context.getResources().getString(R.string.mandatory_messages), context.getResources().getString(field));
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
