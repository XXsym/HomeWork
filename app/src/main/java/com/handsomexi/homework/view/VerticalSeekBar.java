package com.handsomexi.homework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
    public class VerticalSeekBar extends AppCompatSeekBar
    {
        private boolean mIsDragging;
        private float mTouchDownY;
        private int mScaledTouchSlop;
        private boolean isInScrollingContainer = false;

        public boolean isInScrollingContainer()
        {
            return isInScrollingContainer;
        }

        public void setInScrollingContainer(boolean isInScrollingContainer)
        {
            this.isInScrollingContainer = isInScrollingContainer;
        }


        float mTouchProgressOffset;

        public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle)
        {
            super(context, attrs, defStyle);
            mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        }

        public VerticalSeekBar(Context context, AttributeSet attrs)
        {
            super(context, attrs);
        }

        public VerticalSeekBar(Context context)
        {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh)
        {
            super.onSizeChanged(h, w, oldh, oldw);
        }

        @Override
        protected synchronized void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
        {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
        }

        @Override
        protected synchronized void onDraw(Canvas canvas)
        {
           /* //顺针旋转90度，
            canvas.rotate(90);
            canvas.translate(0, -getWidth());*/
            //逆时针旋转90度
            canvas.rotate(-90);
            canvas.translate(-getHeight(), 0);//以原点纵坐标设置为负(滑块高度)
            super.onDraw(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            if (!isEnabled())
            {
                return false;
            }

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN://按下
                    if (isInScrollingContainer())
                    {
                        mTouchDownY = event.getY();
                    }
                    else
                    {
                        setPressed(true);
                        invalidate();
                        onStartTrackingTouch();
                        trackTouchEvent(event);
                        attemptClaimDrag();
                        onSizeChanged(getWidth(), getHeight(), 0, 0);
                    }
                    break;

                case MotionEvent.ACTION_MOVE://移动
                    if (mIsDragging)
                    {
                        trackTouchEvent(event);
                    }
                    else
                    {
                        final float y = event.getY();
                        if (Math.abs(y - mTouchDownY) > mScaledTouchSlop)
                        {
                            setPressed(true);
                            invalidate();
                            onStartTrackingTouch();
                            trackTouchEvent(event);
                            attemptClaimDrag();
                        }
                    }
                    onSizeChanged(getWidth(), getHeight(), 0, 0);
                    break;

                case MotionEvent.ACTION_UP://抬起
                    if (mIsDragging)
                    {
                        trackTouchEvent(event);
                        onStopTrackingTouch();
                        setPressed(false);
                    }
                    else
                    {
                        onStartTrackingTouch();
                        trackTouchEvent(event);
                        onStopTrackingTouch();
                    }
                    onSizeChanged(getWidth(), getHeight(), 0, 0);
                    invalidate();
                    break;
            }
            return true;
        }

        private void trackTouchEvent(MotionEvent event)
        {
            final int height = getHeight();
            final int top = getPaddingTop();
            final int bottom = getPaddingBottom();
            final int available = height - top - bottom;

            int y = (int) event.getY();



            float scale;
            float progress = 0;


            if (y > height - bottom)
            {
                scale = 0.0f;
            }
            else if (y < top)
            {
                scale = 1.0f;
            }
            else
            {
                scale = (float) (available - y + top) / (float) available;
                progress = mTouchProgressOffset;
            }

            final int max = getMax();
            progress = progress + scale * max;
          //  progress = max - progress;//顺时针旋转90度使用，逆时针旋转90度屏蔽此段
            setProgress((int) progress);
        }

        /**
         * 按下
         */
        void onStartTrackingTouch()
        {
            mIsDragging = true;
        }

        /**
         * 松开
         */
        void onStopTrackingTouch()
        {
            mIsDragging = false;
        }

        private void attemptClaimDrag()
        {
            ViewParent p = getParent();
            if (p != null)
            {
                p.requestDisallowInterceptTouchEvent(true);
            }
        }

        @Override
        public synchronized void setProgress(int progress)
        {
            super.setProgress(progress);
            onSizeChanged(getWidth(), getHeight(), 0, 0);
        }
    }
