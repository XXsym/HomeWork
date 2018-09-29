package com.handsomexi.homework.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.handsomexi.homework.Myapp;

public class PermissionUtil {
    private static String[] Permissions=new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    public static boolean allGranted() {
        Context context = Myapp.getInstances();
        for (String permission : Permissions)
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }
    public static void checkPermissions(Activity activity){
        if(!allGranted()) {
            ActivityCompat.requestPermissions(activity, Permissions,1);
        }
    }
}
