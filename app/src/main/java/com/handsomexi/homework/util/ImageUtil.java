package com.handsomexi.homework.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.provider.MediaStore;

import com.handsomexi.homework.activity.CameraActivity;


public class ImageUtil {

    public static void openCamera(Activity activity){
        activity.startActivityForResult(new Intent(activity,CameraActivity.class),666);
    }
    public static void selectFormSystem(Activity activity){
        Intent intent = new Intent().setAction(Intent.ACTION_PICK)
                .setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent,666);
    }

    public static Bitmap removeColor(Bitmap bitmap,boolean red,boolean green,boolean blue){
        //初始化数组
        float[] rgba = new float[20];
        for (int i = 0; i < rgba.length; i++)  rgba[i] = 0;
        rgba[18] = 1;
        if(!red) rgba[0] = 1;
        if(!green) rgba[6] = 1;
        if(!blue) rgba[12] = 1;
        //创建新的bitmap
        Bitmap map = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        //初始化画笔
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(rgba)));
        //绘制Bitmap
        new Canvas(map).drawBitmap(bitmap,0,0,paint);
        return map;
    }

}
