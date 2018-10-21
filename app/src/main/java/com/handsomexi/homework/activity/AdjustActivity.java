package com.handsomexi.homework.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.blankj.utilcode.util.BarUtils;
import com.handsomexi.homework.R;
import com.handsomexi.homework.util.ImageUtil;
import com.handsomexi.homework.view.BottomRow;
import com.handsomexi.homework.view.CircleWidthView;
import com.handsomexi.homework.view.EraserImageView;
import com.handsomexi.homework.view.VerticalSeekBar;

import org.opencv.core.Mat;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AdjustActivity extends AppCompatActivity {


    boolean removeRed = false;
    boolean removeBlue = false;

    @BindView(R.id.crop_pro)
    ProgressBar progressBar;

    @BindView(R.id.imageView)
    EraserImageView eraserImage;


    Bitmap originalMap;
    Mat binaryMat;
    @BindView(R.id.crop_circlewidth)
    CircleWidthView circlewidth;
    @BindView(R.id.crop_seekbar)
    VerticalSeekBar seekbar;
    @BindView(R.id.adjust_linear2)
    LinearLayout linear2;
    @BindView(R.id.adjust_linear3)
    LinearLayout linear3;
    @BindView(R.id.adjust_b1)
    BottomRow adjustB1;
    @BindView(R.id.adjust_b2)
    BottomRow adjustB2;
    @BindView(R.id.adjust_wipered)
    BottomRow wipered;
    @BindView(R.id.adjust_wipeblue)
    BottomRow wipeblue;

    @OnClick({R.id.adjust_b1, R.id.adjust_b2, R.id.adjust_revoke,R.id.adjust_sure})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.adjust_b1: {
                if (adjustB1.isSelected) {
                    adjustB1.unSelected();
                    linear2.setVisibility(View.GONE);
                } else {
                    adjustB1.select();
                    adjustB2.unSelected();
                    linear2.setVisibility(View.VISIBLE);
                    linear3.setVisibility(View.GONE);
                }
                break;
            }
            case R.id.adjust_b2: {
                if (adjustB2.isSelected) {
                    adjustB2.unSelected();
                    linear3.setVisibility(View.GONE);
                } else {
                    adjustB2.select();
                    adjustB1.unSelected();
                    linear3.setVisibility(View.VISIBLE);
                    linear2.setVisibility(View.GONE);
                }
                break;
            }
            case R.id.adjust_revoke:{
                eraserImage.revoke();
                break;
            }
            case R.id.adjust_sure:{
                File file = ImageUtil.saveImage(eraserImage.getBitmap());
                startActivity(new Intent(this,SaveSubjectActivity.class).putExtra("path",file.getPath()));
                finish();
                break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarAlpha(this);
        setContentView(R.layout.activity_adjust);
        ButterKnife.bind(this);
        wipered.setOnClickListener(v -> {
            removeRed = !removeRed;
            if (removeRed) wipered.select();
            else wipered.unSelected();
            removeColor();
        });
        wipeblue.setOnClickListener(v -> {
            removeBlue = !removeBlue;
            if (removeBlue) wipeblue.select();
            else wipeblue.unSelected();
            removeColor();
        });
        //设置图片
        setBitmap();
        //设置seekbar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                eraserImage.setPaintWidth(progress);
                circlewidth.setWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekbar.setProgress(35);
        circlewidth.setWidth(35);
    }


    @SuppressLint("CheckResult")
    void setBitmap() {

        Intent data = getIntent();
        String result = data.getStringExtra("path");
        Observable.just(result)
                .map(s -> {
                    File file = new File(result);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap bitmap = BitmapFactory.decodeFile(result, options);
                    originalMap = ImageUtil.reSize(bitmap);
                    binaryMat = ImageUtil.getBinary(originalMap, 18);
                    file.delete();
                    return ImageUtil.mat2Bitmap(binaryMat, null, false);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    eraserImage.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                });
    }

    private void removeColor() {
        progressBar.setVisibility(View.VISIBLE);
        AtomicLong start = new AtomicLong(System.currentTimeMillis());
        ImageUtil.wipeColor(originalMap, binaryMat, removeRed, removeBlue, bitmap -> {
            progressBar.setVisibility(View.GONE);
            eraserImage.setImageBitmap(bitmap);
            long min = System.currentTimeMillis() - start.get();
            Log.e("info", "图片大小:" + originalMap.getWidth() + "*" + originalMap.getHeight() + "    用时:" + (min) / 1000.0f + "秒");
        });
    }
}
