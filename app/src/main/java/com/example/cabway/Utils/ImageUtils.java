package com.example.cabway.Utils;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.database.Utills.AppConstants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

public class ImageUtils {
    public static final int IMAGE_PICK = 2001;
    private static final String IMAGE_UTILS = "image_utills";

    public static void pickImage(Activity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(AppConstants.IMAGE_MIME_TYPE);
        context.startActivityForResult(
                Intent.createChooser(intent, AppConstants.IMAGE_PICKER_MESSAGE),
                IMAGE_PICK);
    }

    public static String onImagePickResult(int requestCode, int resultCode, Intent data, Context context) {
        String filePath = "";
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            String fileName = new Date().getTime() + ".jpeg";
            Uri uri = data.getData();
            Bitmap bitmap = getContactBitmapFromURI(context, uri);
            if (bitmap != null)
                filePath = saveBitmapIntoSDCardImage(bitmap, fileName, context);
        }
//        Toast.makeText(context, filePath, Toast.LENGTH_SHORT).show();
        return filePath;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj,
                null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
//        Toast.makeText(context, filePath, Toast.LENGTH_SHORT).show();
        return filePath;
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

        ContextWrapper cw = new ContextWrapper(context); // use this code for storing image in internal storage. which has limitation of 70 mb
        File directory = cw.getDir(context.getString(R.string.app_name), Context.MODE_PRIVATE);

//        File directory = new File(Environment.getExternalStorageDirectory().toString() + "/" + context.getString(R.string.app_name)); // external storage
        if (!directory.exists()) {
            directoryAvailable = directory.mkdir();
        }

        if (directoryAvailable) {
            File mypath = new File(directory, fileName);

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(mypath);
                if (finalBitmap != null)
                    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 5, fos);
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


    public static void setImageFromUrl(Context context, String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_profile_icon)
//                .error(R.drawable.ic_add_profile)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "Sucess to Load Image", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(context, "Failed to Load Image", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
    }

    public static void setImageFromFilePath(Context context, String filePath, ImageView imageView) {
        Picasso.get()
                .load(new File(filePath))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.ic_profile_icon)
//                .error(R.drawable.ic_add_profile)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
//                        Toast.makeText(context,"Failed to Load Image",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
    }

}
