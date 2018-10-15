package com.handsomexi.homework.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.handsomexi.homework.R;
import com.handsomexi.homework.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    Bitmap bitmap;
    @BindView(R.id.testimg)
    ImageView img;
    @BindView(R.id.seekBar5)
    SeekBar s3;
    @BindView(R.id.seekBar4)
    SeekBar s6;
    @BindView(R.id.seekBar3)
    SeekBar s5;
    @BindView(R.id.seekBar2)
    SeekBar s2;
    @BindView(R.id.testseek0)
    SeekBar s1;
    @BindView(R.id.seekBar)
    SeekBar s4;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        bitmap = BitmapFactory.decodeFile("/storage/emulated/0/123.png");
        img.setImageBitmap(bitmap);
        s1.setOnSeekBarChangeListener(this);
        s2.setOnSeekBarChangeListener(this);
        s3.setOnSeekBarChangeListener(this);
        s4.setOnSeekBarChangeListener(this);
        s5.setOnSeekBarChangeListener(this);
        s6.setOnSeekBarChangeListener(this);

    }

    private double gk(SeekBar seekBar, double a) {
        double f = (seekBar.getProgress() + 1.0) / a;
        Log.e("seek:", f + "");
        return f;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progressBar.setVisibility(View.VISIBLE);
        int a1 = (int) gk(s1, 1.0f);
        double a2 = gk(s2, 0.5f);
        double a3 = gk(s3, 0.5f);
        double a4 = gk(s4, 1.0f);
        int a5 = (int) gk(s5, 50.0f);
        double a6 = gk(s6, 50.0f);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
