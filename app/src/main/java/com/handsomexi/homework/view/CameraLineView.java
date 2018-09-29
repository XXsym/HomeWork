package com.handsomexi.homework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CameraLineView extends View {
    Paint paint;
    public CameraLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();//初始化画笔
        paint.setColor(Color.WHITE);//设置颜色为白色
        paint.setStrokeWidth(3);//设置宽度为3
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();//获取宽高
        int height = getMeasuredHeight();
        //3*3的网格
        int x = width/3;
        int y = height/3;
        //绘制网格线
        for(int i = 1 ; i <= 2 ; i++){
            canvas.drawLine(x * i, 0, x * i, height, paint);
        }

        for (int i = 1; i <= 2; i++) {
            canvas.drawLine(0, y * i, width, y * i, paint);
        }
    }
}
