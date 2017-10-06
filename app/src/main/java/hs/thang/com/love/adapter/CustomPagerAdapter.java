package hs.thang.com.love.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hs.thang.com.love.view.tab.MainFragment;
import hs.thang.com.love.view.tab.SettingFragment;
import hs.thang.com.love.view.tab.TimeFragment;

/**
 * Created by sev_user on 3/21/2017.
 */

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private Context mContext;

    public CustomPagerAdapter(Context context,FragmentManager fm, int numOfTabs) {
        super(fm);
        mContext = context;
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TimeFragment(mContext);
            case 1:
                return new MainFragment(mContext);
            case 2:
                return new SettingFragment(mContext);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
