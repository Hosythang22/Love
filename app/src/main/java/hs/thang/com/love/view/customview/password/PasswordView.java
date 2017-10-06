package hs.thang.com.love.view.customview.password;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import hs.thang.com.thu.R;

public class PasswordView extends FrameLayout {

    private PasswordState mPassword1;
    private PasswordState mPassword2;
    private PasswordState mPassword3;
    private PasswordState mPassword4;

    public PasswordView(Context context) {
        super(context);
        init();
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.password_view, this);
        mPassword1 = $(R.id.password1);
        mPassword2 = $(R.id.password2);
        mPassword3 = $(R.id.password3);
        mPassword4 = $(R.id.password4);
    }

    private <T extends PasswordState> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    public void setStatePasswordView(boolean onOrOff, int count) {
        switch (count) {
            case 0:
                if (mPassword1.getState()) {
                    mPassword1.setInflate(onOrOff); // off
                }
                if (mPassword2.getState()) {
                    mPassword2.setInflate(onOrOff); // off
                }
                if (mPassword3.getState()) {
                    mPassword3.setInflate(onOrOff); // off
                }
                if (mPassword4.getState()) {
                    mPassword4.setInflate(onOrOff); // off
                }
                break;
            case 1:
                mPassword1.setInflate(onOrOff);
                break;
            case 2:
                mPassword2.setInflate(onOrOff);
                break;
            case 3:
                mPassword3.setInflate(onOrOff);
                break;
            case 4:
                mPassword4.setInflate(onOrOff);
                break;
            default:
                break;
        }

    }
}
