package com.handsomexi.homework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handsomexi.homework.R;

public class BottomRow extends LinearLayout {
    ImageView imageView;
    TextView textView;
    int selected,unselected;

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
        textView.setText(array.getText(R.styleable.BottomRow_text));
        array.recycle();

        selected = ContextCompat.getColor(context,R.color.orange);
        unselected = Color.GRAY;
        setOrientation(LinearLayout.VERTICAL);

    }
    public void select(){
        imageView.setColorFilter(selected);
        textView.setTextColor(selected);
    }
    public void unSelected(){
        imageView.setColorFilter(unselected);
        textView.setTextColor(unselected);
    }
}
