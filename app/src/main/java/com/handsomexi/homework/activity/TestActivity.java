package com.handsomexi.homework.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.handsomexi.homework.R;
import com.handsomexi.homework.util.ImageUtil;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.testimg)
    ImageView img;

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.editText)
    EditText e1;
    @BindView(R.id.editText2)
    EditText e2;
    @BindView(R.id.editText3)
    EditText e3;
    @BindView(R.id.button2)
    Button button2;

    Bitmap bitmap;
    Mat hsv;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        button.setOnClickListener(v -> {
            bitmap = BitmapFactory.decodeFile("/storage/emulated/0/" + e1.getText().toString());
            img.setImageBitmap(bitmap);
            hsv = ImageUtil.bitmap2Mat(bitmap);
            Imgproc.cvtColor(hsv, hsv, Imgproc.COLOR_BGRA2BGR);
            Imgproc.cvtColor(hsv, hsv, Imgproc.COLOR_BGR2HSV);
        });
        button2.setOnClickListener(v -> {
            int a = Integer.valueOf(e2.getText().toString());
            int b = Integer.valueOf(e3.getText().toString());
            print(a,b);
        });

    }
    private void print(int i, int j) {
        double[] c = hsv.get(i, j);
        Log.e(i + " " + j, "H:" + c[0] + "S:" + c[1] + "V:" + c[2]);
    }


}
