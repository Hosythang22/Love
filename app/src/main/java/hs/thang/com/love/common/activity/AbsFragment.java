package hs.thang.com.love.common.activity;

import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class AbsFragment extends Fragment {

    private static final String TAG = "AbsFragment";

    protected View mFragmentView = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cleanupResources();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        cleanupResources();
        super.onDestroy();
    }

    private void cleanupResources() {
        Log.d(TAG, "cleanupResources()");

        if (mFragmentView != null) {
            //ClockUtils.nullViewDrawablesRecursive(mFragmentView);
        }

        mFragmentView = null;
    }
}
