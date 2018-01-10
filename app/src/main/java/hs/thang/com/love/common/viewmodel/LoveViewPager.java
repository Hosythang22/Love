package hs.thang.com.love.common.viewmodel;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by sev_user on 1/4/2018.
 */

public class LoveViewPager extends ViewPager {

    public boolean mIsEnabled; // To block swipping
    private Context mContext;
    private static final String TAG = "LoveViewPager";

    public LoveViewPager(Context context) {
        super(context);
    }

    public LoveViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mIsEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        try {
            return mIsEnabled && super.onTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "onTouchEvent() - IllegalArgumentException");
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return mIsEnabled && super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "onInterceptTouchEvent() - IllegalArgumentException");
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
