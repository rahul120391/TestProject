package com.example.myproject;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import static com.example.myproject.Constants.ANDROID_MINIMUM_VERSION;
import static com.example.myproject.Constants.READ_EXTERNAL_STORAGE_REQUEST_CODE;
import static com.example.myproject.Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE;

public class PermissionChecker {

    private PermissionChecker(){

    }

    public  static boolean isReadStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= ANDROID_MINIMUM_VERSION) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
                return false;
            }
        }
        else {
            return true;
        }
    }

    public  static boolean isWriteStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= ANDROID_MINIMUM_VERSION) {
            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                return false;
            }
        }
        else {
            return true;
        }
    }
}
