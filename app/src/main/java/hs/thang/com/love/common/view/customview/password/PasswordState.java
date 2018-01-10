package hs.thang.com.love.view.customview.password;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import hs.thang.com.thu.R;

public class PasswordState extends FrameLayout {

    private boolean mState; // true: on, false: off

    public PasswordState(Context context) {
        super(context);
        init();
    }

    public PasswordState(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordState(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.password_view_off, this);
    }

    public void setInflate(boolean on) {
        removeAllViewsInLayout();
        mState = on;
        if (on) {
            inflate(getContext(), R.layout.password_view_on, this);
        } else {
            inflate(getContext(), R.layout.password_view_off, this);
        }

        requestLayout();
        invalidate();
    }

    public boolean getState() {
        return mState;
    }
}
