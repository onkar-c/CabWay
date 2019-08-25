package com.example.cabway.Utils;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.database.Utills.AppConstants;

import static com.example.database.Utills.AppConstants.ADDRESS_LENGTH;
import static com.example.database.Utills.AppConstants.MOBILE_NUMBER_LENGTH;
import static com.example.database.Utills.AppConstants.PIN_CODE_LENGTH;

public class TextValidationUtils {

    public static boolean isEmpty(String stringToCheck) {
        return (stringToCheck == null || stringToCheck.trim().length() == 0);
    }

    public static boolean validateMobileNumber(String mobileNumber) {
        return (mobileNumber.length() == MOBILE_NUMBER_LENGTH || !mobileNumber.matches("[6789]\\d{9}$"));
    }

    public static boolean isValidEmail(CharSequence emailId) {
        return (!isEmpty(emailId.toString()) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches());
    }

    public static boolean isValidAddress(String address, Context context){
        if (isEmpty(address)){
            showMandatoryError(R.string.address, context);
            return false;
        } else if (address.trim().length() < ADDRESS_LENGTH) {
            showMandatoryErrorUsingString(context.getString(R.string.address_length), context);
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(String password, String confirmPassword, Context context){
        if (isEmpty(password)) {
            showMandatoryError(R.string.password, context);
            return false;
        } else if (password.trim().length() < AppConstants.PASSWORD_LENGTH) {
            showMandatoryErrorUsingString(context.getString(R.string.password_length), context);
            return false;
        }if (isEmpty(confirmPassword)) {
            showMandatoryError(R.string.confirm_password, context);
            return false;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(context, R.string.password_mismatch_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isValidPinCode(String pinCode) {
        return isEmpty(pinCode) || pinCode.length()!= PIN_CODE_LENGTH ;
    }

    public static void showMandatoryError(int field, Context context) {
        String message = String.format(context.getResources().getString(R.string.mandatory_messages), context.getResources().getString(field));
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showMandatoryErrorUsingString(String field, Context context) {
        String message = String.format(context.getResources().getString(R.string.mandatory_messages), field);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
