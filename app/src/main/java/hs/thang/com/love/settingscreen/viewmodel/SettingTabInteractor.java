package hs.thang.com.love.settingscreen.viewmodel;

import android.app.Activity;
import android.content.Context;

import thang.hs.com.colorpicker.ColorPicker;

/**
 * Created by sev_user on 1/9/2018.
 */

public class SettingTabInteractor implements SettingTabContract.Interactor {
    private SettingTabContract.OnGetThemeColorListener mOnGetThemeColorListener;
    private SettingTabContract.OnGetFontListener mOnGetFontListener;
    private SettingTabContract.OngetTextColorListener mOngetTextColorListener;

    public SettingTabInteractor(SettingTabContract.OnGetThemeColorListener getThemeColorListener,
                                SettingTabContract.OnGetFontListener getFontListener,
                                SettingTabContract.OngetTextColorListener ongetTextColorListener) {
        mOnGetThemeColorListener = getThemeColorListener;
        mOnGetFontListener = getFontListener;
        mOngetTextColorListener = ongetTextColorListener;
    }

    public SettingTabInteractor(SettingTabContract.OnGetThemeColorListener getThemeColorListener) {
        mOnGetThemeColorListener = getThemeColorListener;
    }

    public SettingTabInteractor(SettingTabContract.OngetTextColorListener ongetTextColorListener) {
        mOngetTextColorListener = ongetTextColorListener;
    }

    public SettingTabInteractor(SettingTabContract.OnGetFontListener getFontListener) {
        mOnGetFontListener = getFontListener;
    }

    @Override
    public void changeThemeColor(Context context) {
        ColorPicker colorPicker = new ColorPicker((Activity) context);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {

                mOnGetThemeColorListener.onGetThemeColorDone(color);
            }

            @Override
            public void onCancel() {

            }
        }).show();
    }

    @Override
    public void changeFont(Context context) {
        FontPicker fontPicker = new FontPicker((Activity) context);
        fontPicker.setOnChooseFontListener(new FontPicker.OnChooseFontListener() {
            @Override
            public void onChooseFont(int position, int font) {
                mOnGetFontListener.onGetFontDone(font);
            }

            @Override
            public void onCancel() {

            }
        }).show();
    }

    @Override
    public void changeTextColor(Context context, int textType) {
        ColorPicker colorPicker = new ColorPicker((Activity) context);
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {

                mOngetTextColorListener.onGetTextColorLister(color,textType );
            }

            @Override
            public void onCancel() {

            }
        }).show();
    }
}
