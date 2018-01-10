package hs.thang.com.love.settingscreen.viewmodel;

import android.content.Context;

/**
 * Created by sev_user on 1/9/2018.
 */

public interface SettingTabContract {

    interface View {

        void onChangeThemeColor(int color);

        void onChangeFont(int fontType);

        void onChangeTextColor(int color, int textType);

    }

    interface Presenter {

        void changeThemeColor(Context context);

        void changeFont(Context context);

        void changeTextColor(Context context, int textType);
    }

    interface Interactor {
        void changeThemeColor(Context context);

        void changeFont(Context context);

        void changeTextColor(Context context, int textType);
    }

    interface OnGetThemeColorListener {

        void onGetThemeColorDone(int color);
    }

    interface OnGetFontListener {

        void onGetFontDone(int fontType);
    }

    interface OngetTextColorListener {

        void onGetTextColorLister(int color, int textType);

    }

}
