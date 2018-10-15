package com.handsomexi.homework.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerUtil extends RecyclerView.ItemDecoration{

    private float mDividerHeight;
    private Paint mPaint;

    public DividerUtil(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);       //设置抗锯齿，使边缘更平滑
        mPaint.setColor(Color.BLACK);    //设置颜色
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0 ; i< childCount ; i++){
            //获取每一个itemView
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);
            if (index == 0){
                continue;
            }
            float dividerTop = view.getTop() - mDividerHeight;                   //计算分割线的距离左上角的 y 轴的距离
            float dividerLeft = parent.getPaddingLeft();                        //计算分割线的距离左上角的 x 轴的距离
            float dividerButtom = view.getTop();                                //计算分割线的距离右上角的 y 轴的距离
            float dividerRight = parent.getWidth() - parent.getPaddingRight();  //计算分割线的距离左上角的 x 轴的距离

            c.drawRect(dividerLeft,dividerTop,dividerRight,dividerButtom,mPaint);  //绘制一个矩形根据上面的计算的坐标点绘制
        }
    }


    /*  这个方法是针对每一个item的
      * params：
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //第一个itemView上面不需要分割线
        if (parent.getChildAdapterPosition(view) != 0){
            outRect.top = 1;
            //记录每一个item的上边距
            mDividerHeight = 1;
        }
    }
}
