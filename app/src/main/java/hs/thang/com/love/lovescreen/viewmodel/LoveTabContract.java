package hs.thang.com.love.lovescreen.viewmodel;

import android.content.Context;
import android.net.Uri;

/**
 * Created by sev_user on 1/8/2018.
 */

public interface LoveTabContract {

    interface View {
        void onHeartColorChange(int color);
    }

    interface Presenter {
        void changeHeartColor(Context context);
    }

    interface Interactor {
        void changeHeartColor(Context context);
    }

    interface OnGetHeartColorListener {
        void onGetHeartColor(int color);
    }
}
