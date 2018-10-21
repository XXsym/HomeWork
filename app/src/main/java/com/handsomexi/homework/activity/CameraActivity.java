package com.handsomexi.homework.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.handsomexi.homework.R;
import com.handsomexi.homework.util.ImageUtil;
import com.handsomexi.homework.view.CameraLineView;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.camera_cameraView)
    CameraView cameraView;
    @BindView(R.id.camera_flash)
    ImageView imageView;
    @BindView(R.id.camera_cropview)
    CropImageView cropview;
    @BindView(R.id.camera_linear1)
    LinearLayout linear1;
    @BindView(R.id.camera_linear2)
    LinearLayout linear2;
    @BindView(R.id.cameraLineView)
    CameraLineView lineView;

    @OnClick({R.id.camera_album, R.id.camera_flash, R.id.camera_paizhao, R.id.camera_sure, R.id.camera_rotate})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_album: {
                //打开相册选择图片
                ImageUtil.selectFormSystem(this);
                break;
            }
            case R.id.camera_flash: {
                int stats = cameraView.getFlash();
                if (stats == CameraView.FLASH_TORCH) {//关闭闪光灯
                    cameraView.setFlash(CameraView.FLASH_OFF);
                    imageView.setImageResource(R.mipmap.flash);
                } else {//开启闪光灯
                    cameraView.setFlash(CameraView.FLASH_TORCH);
                    imageView.setImageResource(R.mipmap.flash_on);
                }
                break;
            }
            case R.id.camera_paizhao: {
                cameraView.takePicture();//拍照
                break;
            }
            case R.id.camera_cameraView: {
                cameraView.setAutoFocus(true);
                break;
            }
            case R.id.camera_sure: {
                savaCrop();
                break;
            }
            case R.id.camera_rotate: {
                cropview.rotateImage(90);
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarAlpha(this);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        setResult(0);
        cameraView.addCallback(new CameraView.Callback() {
            @Override
            public void onCameraOpened(CameraView cameraView) {
                super.onCameraOpened(cameraView);
                cameraView.setAspectRatio(AspectRatio.of(1920, 1080));//设置图片尺寸
            }

            @Override
            public void onCameraClosed(CameraView cameraView) {
                super.onCameraClosed(cameraView);
            }

            @Override
            public void onPictureTaken(CameraView cameraView, byte[] data) {
                //在这里保存拍到的图片
                super.onPictureTaken(cameraView, data);
                cameraView.setFlash(CameraView.FLASH_OFF);
                setCropview(ImageUtil.getBitmapFromByte(data));
            }
        });
        cropview.setAutoZoomEnabled(true);
        cropview.setGuidelines(CropImageView.Guidelines.ON);
        cropview.setScaleType(CropImageView.ScaleType.FIT_CENTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setCropview(ImageUtil.getBitmapFromUri(data.getData()));
        } else {
            ToastUtils.showShort("从相册获取图片失败.ERRORCODE:1");
        }
    }

    private void setCropview(Bitmap bitmap) {
        if (bitmap == null) {
            ToastUtils.showShort("获取图片失败");
            return;
        }
        cameraView.stop();
        cameraView.setFlash(CameraView.FLASH_OFF);
        cameraView.setVisibility(View.GONE);
        linear1.setVisibility(View.GONE);
        lineView.setVisibility(View.GONE);

        cropview.setVisibility(View.VISIBLE);
        linear2.setVisibility(View.VISIBLE);
        cropview.setImageBitmap(bitmap);
    }

    private void savaCrop() {
        Bitmap bitmap = cropview.getCroppedImage();
        File file = ImageUtil.saveImage(bitmap);
        setResult(1, new Intent().putExtra("path", file.getPath()));
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
        cameraView.setAutoFocus(false);
        cameraView.setAutoFocus(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraView.stop();
    }


}
