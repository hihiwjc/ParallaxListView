package com.hihiwjc.parallax_listview.ui;

import android.animation.IntEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by hihiwjc on 2015/7/18.
 */
public class ParallaxListView extends ListView {
    private int mHeaderDrawableOriginHeight;
    private int mHeaderOriginHeight;
    private int mHeaderHeight;
    private ImageView mHeader = null;

    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmHeader(ImageView header) {
        this.mHeader = header;
        mHeaderHeight = mHeader.getHeight();
        mHeaderOriginHeight = mHeader.getHeight();
        mHeaderDrawableOriginHeight = mHeader.getDrawable().getIntrinsicHeight();
        Log.i("TAG", "mHeaderDrawableOriginHeight:" + mHeaderDrawableOriginHeight + "  " + mHeader.getLayoutParams().height);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY < 0 && mHeader.getHeight() <= (mHeaderDrawableOriginHeight * 0.5f)) {
            ViewGroup.LayoutParams layoutParams = mHeader.getLayoutParams();
            int newHeight=mHeader.getHeight() + Math.abs(deltaY / 3);
            layoutParams.height = newHeight;
            mHeader.setLayoutParams(layoutParams);
        }

        Log.i("TAG", "deltaX " + deltaX +
                "/deltaY " + deltaY +
                "/scrollX " + scrollX +
                "/scrollY " + scrollY +
                "/scrollRangeX " + scrollRangeX +
                "/scrollRangeY " + scrollRangeY + "" +
                "/maxOverScrollX " + maxOverScrollX +
                "/maxOverScrollY " + maxOverScrollY +
                "/isTouchEvent " + isTouchEvent);

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                ValueAnimator valueAnimator=ValueAnimator.ofInt(1);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float fraction = animation.getAnimatedFraction();
                        int newHeight = new IntEvaluator().evaluate(fraction, mHeader.getHeight(), mHeaderOriginHeight);
                        ViewGroup.LayoutParams layoutParams = mHeader.getLayoutParams();
                        layoutParams.height = newHeight;
                        mHeader.setLayoutParams(layoutParams);
                    }
                });
                valueAnimator.start();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
