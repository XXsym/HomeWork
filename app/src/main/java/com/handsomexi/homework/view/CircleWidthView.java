package com.handsomexi.homework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.handsomexi.homework.R;

public class CircleWidthView extends View {
    private float width ;
    private Paint paint;
    public CircleWidthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context,R.color.white));
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,width,paint);
    }

    public void setWidth(int width) {
        this.width = width/2.0f;
        invalidate();
    }
}
