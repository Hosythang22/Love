package hs.thang.com.love.lovescreen.viewmodel;

import android.app.Activity;
import android.content.Context;

import thang.hs.com.colorpicker.ColorPicker;

/**
 * Created by sev_user on 1/8/2018.
 */

public class LoveTabInteractor implements LoveTabContract.Interactor {

    private LoveTabContract.OnGetHeartColorListener mOnGetHeartColorListener;

    public LoveTabInteractor(LoveTabContract.OnGetHeartColorListener listener) {
        mOnGetHeartColorListener = listener;
    }

    @Override
    public void changeHeartColor(Context context) {
        ColorPicker colorPicker = new ColorPicker((Activity) context);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {

                mOnGetHeartColorListener.onGetHeartColor(color);
            }

            @Override
            public void onCancel() {

            }
        }).show();
    }
}
