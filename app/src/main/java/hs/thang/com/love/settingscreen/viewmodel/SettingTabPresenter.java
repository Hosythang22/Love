package hs.thang.com.love.settingscreen.viewmodel;

import android.content.Context;

/**
 * Created by sev_user on 1/9/2018.
 */

public class SettingTabPresenter implements SettingTabContract.Presenter, SettingTabContract.OnGetThemeColorListener,
        SettingTabContract.OnGetFontListener, SettingTabContract.OngetTextColorListener{

    private SettingTabContract.View mView;
    private SettingTabInteractor mInteractor;

    public SettingTabPresenter(SettingTabContract.View view) {
        mView = view;
        mInteractor = new SettingTabInteractor(this, this, this);
    }
    @Override
    public void changeThemeColor(Context context) {
        mInteractor.changeThemeColor(context);
    }

    @Override
    public void changeFont(Context context) {
        mInteractor.changeFont(context);
    }

    @Override
    public void changeTextColor(Context context, int textType) {
        mInteractor.changeTextColor(context, textType);
    }

    @Override
    public void onGetThemeColorDone(int color) {
        mView.onChangeThemeColor(color);
    }

    @Override
    public void onGetFontDone(int fontType) {
        mView.onChangeFont(fontType);
    }

    @Override
    public void onGetTextColorLister(int fontType, int textType) {
        mView.onChangeTextColor(fontType, textType);
    }
}
