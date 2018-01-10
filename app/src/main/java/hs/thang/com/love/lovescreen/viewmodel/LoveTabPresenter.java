package hs.thang.com.love.lovescreen.viewmodel;

import android.content.Context;
import android.net.Uri;

/**
 * Created by sev_user on 1/8/2018.
 */

public class LoveTabPresenter implements LoveTabContract.Presenter, LoveTabContract.OnGetHeartColorListener {

    private LoveTabContract.View mLoveView;
    private LoveTabInteractor mLoveInteractor;

    public LoveTabPresenter(LoveTabContract.View loveView) {
        mLoveView = loveView;
        mLoveInteractor = new LoveTabInteractor(this);
    }

    @Override
    public void changeHeartColor(Context context) {
        mLoveInteractor.changeHeartColor(context);
    }

    @Override
    public void onGetHeartColor(int color) {
        mLoveView.onHeartColorChange(color);
    }
}
