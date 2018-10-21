package com.handsomexi.homework.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.handsomexi.homework.Myapp;
import com.handsomexi.homework.activity.CameraActivity;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ImageUtil {
    public static void openCamera(Activity activity){
        activity.startActivityForResult(new Intent(activity,CameraActivity.class),666);
    }
    public static void selectFormSystem(Activity activity){
        Intent intent = new Intent().setAction(Intent.ACTION_PICK)
                .setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent,666);
    }
    public static File saveImage(Bitmap bitmap){
        File file = Util.getNewPicFile();
        ImageUtils.save(bitmap,file,Bitmap.CompressFormat.JPEG);
        return file;
    }
    public static Bitmap createQRImage(String url, final int width, final int height) {
        try {
            // 判断URL合法性
            if (StringUtils.isEmpty(url)) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.RGB_565);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
    //缩放Bitmap
    public static Bitmap reSize(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width,height);
        if(max<1500) return bitmap;
        float scale = 1500.0f / max;
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        Bitmap bmp = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        bitmap.recycle();
        return bmp;
    }

    public static Bitmap getBitmapFromByte(byte[] imgByte) {
        InputStream input;
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
        bitmap = (Bitmap) softRef.get();
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static Bitmap getBitmapFromUri(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(Myapp.getInstances().getContentResolver(),uri);
        } catch (IOException e) {
            ToastUtils.showShort("从相册获取图片失败.ERRORCODE:0");
            e.printStackTrace();
        }
        return bitmap;
    }
    //去除颜色
    public static AtomicInteger integer = new AtomicInteger(0);
    public static void wipeColor(Bitmap bitmap,Mat binaryMat,boolean red,boolean blue,OnWipeCallBack back){

        if(!red&&!blue)
            back.finish(mat2Bitmap(binaryMat,null,false));

        Observable.create(emitter -> {
            Mat mat = bitmap2Mat(bitmap);
            Mat mat1 = binaryMat.clone();
            Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGR2HSV);
            int row = mat.rows();
            int col = mat.cols();
            threadHelper(1,4,row,col,mat,mat1,red,blue,emitter);
            threadHelper(2,4,row,col,mat,mat1,red,blue,emitter);
            threadHelper(3,4,row,col,mat,mat1,red,blue,emitter);
            threadHelper(4,4,row,col,mat,mat1,red,blue,emitter);
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(Object o) {
                        integer.set(0);
                        back.finish(mat2Bitmap((Mat) o,null,true));
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() { }
                });
    }
    private static void threadHelper(int m, int n, int row, int col, Mat mat, Mat mat1, boolean red, boolean blue, Emitter emitter){
        AtomicInteger start = new AtomicInteger((m - 1) * (row / n));
        AtomicInteger end = new AtomicInteger(m==n?row:m * (row / n));
        new Thread(()->{
            for (int i = start.get(); i < end.get(); i++) {
                for (int j = 0; j < col; j++) {
                    double[] clone = mat.get(i,j).clone();
                    double h = clone[0];
                    double s = clone[1];
                    double v = clone[2];
                    if ((red&&(h >= 108 && h < 132)&&s>=43&&v>=46) ||
                            (blue&&(h<=12 || h>=168)&&s>=43&&v>=46)) {
                        clone[0] = 255;
                        clone[1] = 255;
                        clone[2] = 255;
                        mat1.put(i, j, clone);
                    }
                }
            }
            if(integer.incrementAndGet()==n) {
                emitter.onNext(mat1);
                Log.e("thread","第"+m+"个线程");
                mat.release();
            }
        }).start();
    }

    //获取二值化图片
    public static Mat getBinary(Bitmap bitmap,int i){
        Mat mat = bitmap2Mat(bitmap);
        Mat mat1 = new Mat();
        Mat mat2 = new Mat();
        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGR2GRAY);
        Imgproc.bilateralFilter(mat,mat1,i, i/2.0f, i/2.0f);
        Imgproc.adaptiveThreshold(mat1,mat2,255.0,0,0,25,10.0);
        mat.release();
        mat1.release();
        return mat2;
    }


    public static Mat bitmap2Mat(Bitmap bitmap){
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap,mat);
        return mat;
    }
    public static Bitmap mat2Bitmap(Mat mat,Bitmap bmp,boolean r){
        if(bmp==null){
            bmp = Bitmap.createBitmap(mat.width(),mat.height(),Bitmap.Config.ARGB_8888);
        }
        Utils.matToBitmap(mat,bmp);
        if(r) mat.release();
        return bmp;
    }
    public interface OnWipeCallBack{
        public void finish(Bitmap bitmap);
    }






}
