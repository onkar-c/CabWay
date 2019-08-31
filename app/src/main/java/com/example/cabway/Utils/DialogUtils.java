package com.example.cabway.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class DialogUtils {

    public static void showMessageDialog(Activity context, String message, DialogInterface.OnClickListener onClickListener){
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", onClickListener)
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss())
                .show();
    }
}
