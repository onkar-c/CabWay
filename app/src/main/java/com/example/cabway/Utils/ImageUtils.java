package com.example.cabway.Utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.cabway.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageUtils {
    public static final int IMAGE_PICK = 2001;
    private static final String IMAGE_UTILS = "image_utills";

    public static void pickImage(Activity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        context.startActivityForResult(
                Intent.createChooser(intent, "select image"),
                IMAGE_PICK);
    }

    public static String onImagePickResult(int requestCode, int resultCode, Intent data, String fileName, Context context) {
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Bitmap bitmap = getContactBitmapFromURI(context, uri);
            if (bitmap != null)
                return saveBitmapIntoSDCardImage(bitmap, fileName, context);
        }
        return "";
    }

    private static Bitmap getContactBitmapFromURI(Context context, Uri uri) {
        try {

            InputStream input = context.getContentResolver().openInputStream(uri);
            if (input == null) {
                return null;
            }
            return BitmapFactory.decodeStream(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static String saveBitmapIntoSDCardImage(Bitmap finalBitmap, String fileName, Context context) {

        boolean directoryAvailable = true;

        /*ContextWrapper cw = new ContextWrapper(context); // use this code for storing image in internal storage. which has limitation of 70 mb
        cw.getDir(context.getString(R.string.app_name), Context.MODE_PRIVATE);*/

        File directory = new File(Environment.getExternalStorageDirectory().toString() + "/" + context.getString(R.string.app_name));
        if (!directory.exists()) {
            directoryAvailable = directory.mkdir();
        }

        if (directoryAvailable) {
            File mypath = new File(directory, fileName);

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(mypath);
                if (finalBitmap != null)
                    finalBitmap.compress(Bitmap.CompressFormat.PNG, 70, fos);
                fos.close();
            } catch (Exception e) {
                Log.e(IMAGE_UTILS, e.getMessage(), e);
                return "";
            }
            Log.e(IMAGE_UTILS, mypath.getAbsolutePath());
            return mypath.getAbsolutePath();
        }
        return "";
    }

}
