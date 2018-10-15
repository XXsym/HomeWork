package com.handsomexi.homework.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;
import com.handsomexi.homework.Myapp;
import com.handsomexi.homework.activity.CameraActivity;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;


public class ImageUtil {
    public static void openCamera(Activity activity){
        activity.startActivityForResult(new Intent(activity,CameraActivity.class),666);
    }
    public static void selectFormSystem(Activity activity){
        Intent intent = new Intent().setAction(Intent.ACTION_PICK)
                .setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent,666);
    }
    public static Bitmap getBitmapFromView(View v){
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        v.draw(c);
        return bmp;
    }
    public static File saveImage(Bitmap bitmap){
        File file = Util.getNewPicFile();
        ImageUtils.save(bitmap,file,Bitmap.CompressFormat.JPEG);
        return file;
    }
    //去除颜色
    public static Bitmap removeColor(Bitmap bitmap,boolean red,boolean blue){
        Bitmap thre = ImageUtil.getBinary(bitmap,19);
        if(!red&&!blue){
            return thre;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        byte[] data = new byte[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = bitmap.getPixel(j,i);
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);
                if(g<210&&((r>=100&&r>Math.max(g,b)&&r>g+15)||(r<100&&r>Math.max(g,b)&&r>g+30))){
                    data[i*width+j] = 1;
                }else if(Math.max(Math.max(g,b),r)<150&&Math.max(Math.max(g,b),r) - Math.min(Math.min(g,b),r)<20){
                    data[i*width+j] = 0;
                }else if(b>240 || b>Math.max(r,g)){
                    data[i*width+j] = 2;
                }
            }
        }
        for (int i = 0;i<height;i++){
            for (int j = 0;j<width;j++){
                if(red && data[i * width + j] == 1){
                    thre.setPixel(j,i,-1);
                }
                if(blue && data[i * width + j] == 2){
                    thre.setPixel(j,i,-1);
                }
            }
        }
        return thre;
    }

    //获取二值化图片
    public static Bitmap getBinary(Bitmap bitmap,int i){
        Mat mat = bitmap2Mat(bitmap);
        Mat mat1 = new Mat();
        Mat mat2 = new Mat();
        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGR2GRAY);
        Imgproc.bilateralFilter(mat,mat1,i, i/2.0f, i/2.0f);
        Imgproc.adaptiveThreshold(mat1,mat2,255.0,0,0,25,10.0);
        mat.release();
        mat1.release();
        return mat2Bitmap(mat2,null);
    }


    private static Mat bitmap2Mat(Bitmap bitmap){
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap,mat);
        return mat;
    }
    private static Bitmap mat2Bitmap(Mat mat,Bitmap bmp){
        if(bmp==null){
            bmp = Bitmap.createBitmap(mat.width(),mat.height(),Bitmap.Config.ARGB_8888);
        }
        Utils.matToBitmap(mat,bmp);
        mat.release();
        return bmp;
    }






}
