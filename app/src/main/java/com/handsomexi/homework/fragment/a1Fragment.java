package com.handsomexi.homework.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.handsomexi.homework.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class a1Fragment extends Fragment {
    @BindView(R.id.a1_banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.a1_chart)
    LineChart chart;

    @OnClick({R.id.a1_img4,R.id.a1_img3,R.id.a1_img2,R.id.a1_img1})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.a1_img1:{
                ToastUtils.showShort("推荐科目");
                break;
            }
            case R.id.a1_img2:{
                ToastUtils.showShort("每日一练");
                break;
            }
            case R.id.a1_img3:{
                ToastUtils.showShort("复习");
                break;
            }
            case R.id.a1_img4:{
                ToastUtils.showShort("签到");
                break;
            }

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_a1, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setBanner();
        setChart();
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setBanner() {
        List<String> url = new ArrayList<>();
        url.add("http://img61.ddimg.cn/2018/9/25/2018092516115013125.jpg");
        url.add("http://img62.ddimg.cn/2018/9/25/201809251617047327.jpg");
        url.add("http://img60.ddimg.cn/2018/9/25/201809251608533265.jpg");
        url.add("http://img62.ddimg.cn/2018/9/25/2018092516353242338.jpg");
        banner.setOnBannerListener(position -> {
            ToastUtils.showShort("点击了" + position);
        });
        banner.setImages(url);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context)
                        .asBitmap()
                        .load(path.toString())
                        .into(imageView);
            }
        });
        banner.start();
    }

    private void setChart() {
        //设置隐藏底部lable
        chart.getXAxis().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        //设置Description
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
        //是否设置网格线
        chart.setDrawGridBackground(false);
        //隐藏XY轴
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        //设置背景
        chart.setBackgroundColor(Color.WHITE);
        //设置是否可以拖动
        chart.setDragEnabled(false);
        //设置是否可以放大
        chart.setDoubleTapToZoomEnabled(false);
        chart.setPinchZoom(false);
        //设置触摸事件
        chart.setTouchEnabled(false);
        //设置Dataset
        LineDataSet dataSet = new LineDataSet(getEntrys(), "label");
        dataSet.setDrawCircles(false);//设置是否绘制曲线值的圆点
        dataSet.setDrawCircleHole(false);//设置曲线值的圆点是实心还是空心 false为实心
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置为曲线
        dataSet.setDrawFilled(true);//设置折线图填充
        dataSet.setValueTextSize(0);//设置字体大小
        dataSet.setLineWidth(2f);//设置线的宽度
        dataSet.setFillColor(Color.argb(255, 209, 233, 252));//设置填充颜色
        dataSet.setColor(Color.argb(255, 98, 166, 252));//设置曲线颜色
        LineData data = new LineData(dataSet);
        chart.setData(data);


    }

    private List<Entry> getEntrys() {
        List<Entry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int num = calendar.get(Calendar.DATE);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < num; i++) {
            entries.add(new Entry(i, random.nextInt(30)));
        }
        return entries;
    }
}
