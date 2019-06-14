package com.example.cabway.Utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

public class DialogUtils {

    public static void showExitDialog(Activity context){
        new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> context.finish())
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss())
                .show();
    }
}
