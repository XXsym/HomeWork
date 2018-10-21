package com.handsomexi.homework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;

public class EraserImageView extends AppCompatImageView {

    private Paint paint;
    //当前正在画的线
    private Line current = new Line();
    //所有画过的线
    private ArrayList<Line> lines = new ArrayList<>();
    //当前线的宽度
    private float currentWidth;

    public EraserImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //画出之前所有的线
        for (int i = 0; i < lines.size(); i++)
        {
            drawLine(canvas, lines.get(i));
        }

        //画出当前的线
        drawLine(canvas, current);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                current.width = currentWidth;
                performClick();
                invalidate();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                ViewPoint point = new ViewPoint();
                point.x = event.getX();
                point.y = event.getY();
                current.points.add(point);
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:{
                lines.add(current);
                current = new Line();
                invalidate();
                break;
            }
        }
        return true;
    }

    public void setPaintWidth(float width){
        currentWidth = width;
    }
    public Bitmap getBitmap(){
        Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmp);
        draw(c);
        return bmp;
    }

    public void revoke(){
        if (lines.size() == 0) return;
        lines.remove(lines.size()-1);
        invalidate();
    }

    private void drawLine(Canvas canvas, Line line){
        paint.setStrokeWidth(line.width);
        for (int i = 0; i < line.points.size() - 1; i++){
            ViewPoint point = line.points.get(i);
            ViewPoint point1 = line.points.get(i+1);
            float x = point.x;
            float y = point.y;

            float x1 = point1.x;
            float y1 = point1.y;

            canvas.drawLine(x,y,x1,y1,paint);
        }
    }

    //代表ImageView上的一点
    public class ViewPoint
    {
        float x;
        float y;
    }

    //表示一条线
    public class Line
    {
        //线的宽度
        float width;
        //点的集合
        ArrayList<ViewPoint> points = new ArrayList<>();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
