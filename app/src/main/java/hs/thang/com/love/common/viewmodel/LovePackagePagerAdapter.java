package hs.thang.com.love.common.viewmodel;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hs.thang.com.love.chatscreen.viewmodel.ChatTabFragment;
import hs.thang.com.love.lovescreen.viewmodel.LoveTabFragment;
import hs.thang.com.love.settingscreen.viewmodel.SettingTabFragment;


public class LovePackagePagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private Context mContext;

    public LovePackagePagerAdapter(Context context, FragmentManager fm, int numOfTabs) {
        super(fm);
        mContext = context;
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChatTabFragment(mContext);
            case 1:
                return new LoveTabFragment(mContext);
            case 2:
                return new SettingTabFragment(mContext);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
