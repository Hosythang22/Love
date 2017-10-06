package hs.thang.com.love.view.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import hs.thang.com.thu.R;

@SuppressLint("ValidFragment")
public class SettingFragment extends AbsFragment {

    private Context mContext;
    private LinearLayout mBackGroundSetting;

    public SettingFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mBackGroundSetting = (LinearLayout) rootView.findViewById(R.id.backgound_frag_setting);
    }
}
