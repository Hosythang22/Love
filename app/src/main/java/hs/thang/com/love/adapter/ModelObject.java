package hs.thang.com.love.adapter;

import hs.thang.com.thu.R;

public enum  ModelObject {

    RED(R.string.red, R.layout.fragment_time),
    BLUE(R.string.blue, R.layout.fragment_main),
    GREEN(R.string.green, R.layout.fragment_setting);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
