package com.example.cabway.Utils;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.cabway.R;
import com.example.database.Utills.AppConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

public class ImageUtils {
    public static final int IMAGE_PICK = 2001;
    private static final String IMAGE_UTILS = "image_utils";

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
        int column_index = Objects.requireNonNull(cursor)
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        //        Toast.makeText(context, filePath, Toast.LENGTH_SHORT).show();
        return cursor.getString(column_index);
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
                    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 30, fos);
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


    public static void setImageFromUrl(Context context, String url, ImageView imageView, int placeHolder) {
            Glide.with(context)
                    .load((url != null) ? url : "")
                    .placeholder(placeHolder)
//                    .centerCrop()
                    .centerInside()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            Toast.makeText(context, "Failed to Load Image", Toast.LENGTH_SHORT).show();
//                            Log.e("TAG", "Error loading image", e);
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);

    }

    public static void setImageFromFilePath(Context context, String filePath, ImageView imageView) {

        if (filePath != null) {
            Glide.with(context)
                    .load(new File(filePath))
                    .placeholder(R.drawable.ic_profile_icon)
                    .centerInside()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Toast.makeText(context, "Failed to Load Image", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "Error loading image", e);
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);

        }

    }

}
