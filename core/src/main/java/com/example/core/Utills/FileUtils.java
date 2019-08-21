package com.example.core.Utills;

import android.text.TextUtils;

import com.example.database.Utills.AppConstants;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtils {
    public static MultipartBody.Part getMultipartBody(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse(AppConstants.FILE_TYPE), file);
            return MultipartBody.Part.createFormData(AppConstants.PART_FILE_TYPE, file.getName(), requestFile);
        }
        return null;
    }
}
