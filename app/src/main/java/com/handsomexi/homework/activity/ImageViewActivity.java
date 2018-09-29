package com.handsomexi.homework.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.bm.library.PhotoView;
import com.handsomexi.homework.R;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ImageViewActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        BarUtils.setStatusBarAlpha(this);//设置状态栏透明
        String path = getIntent().getStringExtra("path");//获取图片位置
        if(path == null || path.trim().isEmpty())
            return;
        PhotoView photoView = (PhotoView) findViewById(R.id.photoview);
        photoView.enable();//开启缩放
        photoView.setImageDrawable(Drawable.createFromPath(path));//设置图片
        photoView.setOnClickListener(v->finish());
    }

}
