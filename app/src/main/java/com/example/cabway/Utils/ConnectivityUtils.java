package com.example.cabway.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectivityUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if(connectivityManager.getActiveNetworkInfo() == null)
                return false;
            else if (connectivityManager
                    .getActiveNetworkInfo().isConnectedOrConnecting())
                return true;
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                    .getActiveNetworkInfo().isConnectedOrConnecting();
        }
       return false;
    }
}