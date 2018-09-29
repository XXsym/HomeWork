package com.handsomexi.homework.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.handsomexi.homework.R;


import butterknife.ButterKnife;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarAlpha(this);

        SliderPage sliderPage = new SliderPage();

        sliderPage.setBgColor(Color.GRAY);
        sliderPage.setDescColor(Color.WHITE);
        sliderPage.setTitleColor(Color.WHITE);
        sliderPage.setImageDrawable(R.mipmap.welcomepic);
        sliderPage.setTitle("我来了,你轻松地学习");
        sliderPage.setDescription("Long long long Description");

        addSlide(AppIntroFragment.newInstance(sliderPage));

        sliderPage = new SliderPage();
        sliderPage.setBgColor(Color.GRAY);
        sliderPage.setDescColor(Color.WHITE);
        sliderPage.setTitleColor(Color.WHITE);
        sliderPage.setImageDrawable(R.mipmap.welcomepic);
        sliderPage.setTitle("请记得陪伴的日子");
        sliderPage.setDescription("Long long long Description");


        addSlide(AppIntroFragment.newInstance(sliderPage));

        setSkipText("跳过");
        setDoneText("进入");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        done();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        done();

    }

    void done(){
        SPUtils.getInstance().put("isFirstUse",false);
        finish();
    }
}
