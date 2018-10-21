package com.handsomexi.homework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.handsomexi.homework.R;

public class BottomRow extends LinearLayout {
    ImageView imageView;
    TextView textView;
    int selected,unselected;
    public boolean isSelected = false;
    private boolean changeImg ;
    public BottomRow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_bottomrow,this);
        imageView = findViewById(R.id.bottomrow_image);
        textView = findViewById(R.id.bottomrow_text);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.BottomRow);
        imageView.setImageResource(array.getResourceId(R.styleable.BottomRow_imagesrc,R.mipmap.home));
        LinearLayout.LayoutParams params = (LayoutParams) imageView.getLayoutParams();
        params.width = params.height = array.getDimensionPixelOffset(R.styleable.BottomRow_img_width,ConvertUtils.dp2px(20));
        imageView.setLayoutParams(params);

        textView.setTextSize(ConvertUtils.px2sp(array.getDimension(R.styleable.BottomRow_text_size,ConvertUtils.sp2px(11))));
        textView.setText(array.getText(R.styleable.BottomRow_text));

        selected = array.getColor(R.styleable.BottomRow_select_color,ContextCompat.getColor(context,R.color.orange));
        unselected = array.getColor(R.styleable.BottomRow_unselect_color,ContextCompat.getColor(context,R.color.actionsheet_gray));

        changeImg = array.getBoolean(R.styleable.BottomRow_changeimg,true);

        if(array.getBoolean(R.styleable.BottomRow_select,false))
            select();
        else
            unSelected();
        setOrientation(LinearLayout.VERTICAL);
        array.recycle();

    }
    public void select(){
        if(changeImg) imageView.setColorFilter(selected);
        textView.setTextColor(selected);
        isSelected = true;
    }
    public void unSelected(){
        if(changeImg) imageView.setColorFilter(unselected);
        textView.setTextColor(unselected);
        isSelected = false;
    }
}
