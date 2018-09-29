package com.handsomexi.homework.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.handsomexi.homework.Myapp;
import com.handsomexi.homework.R;
import com.handsomexi.homework.util.ImageUtil;
import com.handsomexi.homework.util.Util;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelThresholdFilter;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class CropActivity extends SwipeBackActivity {

    @BindView(R.id.crop_cropview)
    CropImageView cropview;
    @BindView(R.id.crop_pro)
    ProgressBar progressBar;

    boolean removeRed = false;
    boolean removeBlue = false;

    Bitmap bitmap;
    @BindView(R.id.crop_qred)
    TextView cropQred;
    @BindView(R.id.crop_qblue)
    TextView cropQblue;
    @BindView(R.id.crop_qother)
    TextView cropQother;

    @OnClick({R.id.crop_qred, R.id.crop_qblue, R.id.crop_qother, R.id.crop_rotate, R.id.crop_sure})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.crop_rotate: {
                cropview.rotateImage(90);
                break;
            }
            case R.id.crop_sure: {
                progressBar.setVisibility(View.VISIBLE);
                cropview.getCroppedImageAsync();
                break;
            }
            case R.id.crop_qblue: {
                removeBlue = !removeBlue;
                if (removeBlue) cropQblue.setTextColor(Color.YELLOW);
                else cropQblue.setTextColor(Color.WHITE);
                cropview.setImageBitmap(ImageUtil.removeColor(bitmap,removeRed,false,removeBlue));
                break;
            }
            case R.id.crop_qred: {
                removeRed = !removeRed;
                if (removeRed) cropQred.setTextColor(Color.YELLOW);
                else cropQred.setTextColor(Color.WHITE);
                cropview.setImageBitmap(ImageUtil.removeColor(bitmap,removeRed,false,removeBlue));
                break;
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarAlpha(this);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        setBitmap();
        cropview.setAutoZoomEnabled(true);
        cropview.setGuidelines(CropImageView.Guidelines.ON);
        cropview.setScaleType(CropImageView.ScaleType.FIT_CENTER);
        cropview.setOnCropImageCompleteListener((view, result) -> {
            File file = Util.getNewPicFile();
            ImageUtils.save(result.getBitmap(), file, Bitmap.CompressFormat.JPEG);
            setResult(1, new Intent().putExtra("path", file.getPath()));
            finish();
        });
    }

    @SuppressLint("CheckResult")
    void setBitmap() {

        Intent data = getIntent();
        String result = data.getStringExtra("data");
        Observable.just(result)
                .map(s -> {
                    Bitmap bitmap;
                    if (s.equals("uri")) {
                        Uri uri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(Myapp.getInstances().getContentResolver(), uri);
                    } else {
                        File file = new File(s);
                        bitmap = new Compressor(CropActivity.this).setQuality(20).compressToBitmap(file);
                        file.delete();
                    }
                    return getGpuImage(bitmap);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    this.bitmap = bitmap;
                    cropview.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                });
    }

    private Bitmap getGpuImage(Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(this);
        GPUImageSobelThresholdFilter filter = new GPUImageSobelThresholdFilter();
        filter.setThreshold(0.7f);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(filter);
        return gpuImage.getBitmapWithFilterApplied();
    }


}
