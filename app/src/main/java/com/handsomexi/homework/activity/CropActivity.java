package com.handsomexi.homework.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.handsomexi.homework.Myapp;
import com.handsomexi.homework.R;
import com.handsomexi.homework.util.ImageUtil;
import com.handsomexi.homework.view.CircleWidthView;
import com.handsomexi.homework.view.EraserImageView;
import com.handsomexi.homework.view.VerticalSeekBar;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.opencv.android.OpenCVLoader;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CropActivity extends AppCompatActivity {

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


    @BindView(R.id.imageView)
    EraserImageView eraserImage;
    @BindView(R.id.crop_linear1)
    LinearLayout cropLinear1;
    @BindView(R.id.crop_linear2)
    LinearLayout cropLinear2;
    @BindView(R.id.crop_seekbar)
    VerticalSeekBar cropSeekbar;
    @BindView(R.id.crop_circlewidth)
    CircleWidthView circleWidth;
    @BindView(R.id.crop_seekbar0)
    SeekBar cropSeekbar0;
    @BindView(R.id.crop_linear0)
    LinearLayout cropLinear0;

    @OnClick({R.id.crop_qred, R.id.crop_qblue, R.id.crop_qother, R.id.crop_rotate, R.id.crop_sure, R.id.crop_sure1, R.id.crop_revoke})
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
                removeColor();
                break;
            }
            case R.id.crop_qred: {
                removeRed = !removeRed;
                if (removeRed) cropQred.setTextColor(Color.YELLOW);
                else cropQred.setTextColor(Color.WHITE);
                removeColor();
                break;
            }
            case R.id.crop_sure1: {
                File file = ImageUtil.saveImage(ImageUtil.getBitmapFromView(eraserImage));
                Intent intent = new Intent(this, SaveSubjectActivity.class);
                intent.putExtra("path", file.getPath());
                startActivity(intent);
                finish();
                break;
            }
            case R.id.crop_revoke: {
                eraserImage.revoke();
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
        //设置图片
        setBitmap();
        //设置裁剪view
        cropview.setAutoZoomEnabled(true);
        cropview.setGuidelines(CropImageView.Guidelines.ON);
        cropview.setScaleType(CropImageView.ScaleType.FIT_CENTER);
        cropview.setOnCropImageCompleteListener((view, result) -> {
            eraserImage.setImageBitmap(result.getBitmap());
            eraserImage.setVisibility(View.VISIBLE);
            cropLinear2.setVisibility(View.VISIBLE);
            cropview.setVisibility(View.GONE);
            cropLinear1.setVisibility(View.GONE);
            cropLinear0.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        });
        //设置seekbar
        cropSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                eraserImage.setPaintWidth(progress);
                circleWidth.setWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        cropSeekbar.setProgress(35);
        circleWidth.setWidth(35);
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
                        bitmap = new Compressor(CropActivity.this).setQuality(10).compressToBitmap(file);
                        file.delete();
                    }
                    this.bitmap = bitmap;
                    return ImageUtil.getBinary(bitmap,19);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    cropview.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                });
    }
    @SuppressLint("CheckResult")
    private void removeColor(){
        progressBar.setVisibility(View.VISIBLE);
        Observable.just("")
                .map(s -> ImageUtil.removeColor(bitmap,removeRed,removeBlue))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    progressBar.setVisibility(View.GONE);
                    cropview.setImageBitmap(bitmap);
                });
    }
}
