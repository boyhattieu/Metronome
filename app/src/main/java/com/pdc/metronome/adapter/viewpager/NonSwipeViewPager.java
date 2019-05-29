package com.pdc.metronome.adapter.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonSwipeViewPager extends ViewPager {

    private boolean mSwipeable = false;

    public NonSwipeViewPager(Context context) {
        super(context);
    }

    public NonSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mSwipeable ? super.onInterceptTouchEvent(event) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mSwipeable ? super.onTouchEvent(event) : false;
    }

    public void setSwipeable(boolean enabled) {
        this.mSwipeable = enabled;
    }
}
