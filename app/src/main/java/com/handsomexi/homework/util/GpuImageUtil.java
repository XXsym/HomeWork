package com.handsomexi.homework.util;

import android.graphics.Bitmap;

import com.handsomexi.homework.Myapp;

import jp.co.cyberagent.android.gpuimage.*;

public class GpuImageUtil {
    private static GPUImageFilter getFilter(int i){
        GPUImageFilter filter;
        switch (i){
            case 1:
                filter = new GPUImageSobelThresholdFilter();
                break;
            case 2:
                filter = new GPUImageHalftoneFilter();
                break;
            case 3:
                filter = new GPUImageSobelEdgeDetection();
                break;
            case 4:
                filter = new GPUImagePixelationFilter();
                break;
            case 5:
                filter = new GPUImageToonFilter();
                break;
            case 6:
                filter = new GPUImageHazeFilter();
                break;
            case 7:
                filter = new GPUImageSmoothToonFilter();
                break;
            case 8:
                filter = new GPUImageThresholdEdgeDetection();
                break;
            case 9:
                filter = new GPUImageSketchFilter();
                break;
            case 10:
                filter = new GPUImageSharpenFilter();
                break;

                default:filter = null;
        }
        return filter;
    }

    public static Bitmap getBitmap(Bitmap bitmap,int filter){
        GPUImage gpuImage = new GPUImage(Myapp.getInstances());
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(getFilter(filter));
        return gpuImage.getBitmapWithFilterApplied();
    }
}
