package hs.thang.com.love.settingscreen.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hs.thang.com.love.MainActivity;
import hs.thang.com.love.chatscreen.chat.utils.Constants;
import hs.thang.com.love.common.activity.AbsTabFragment;
import hs.thang.com.love.common.utils.SharedPrefUtil;
import hs.thang.com.love.R;

@SuppressLint("ValidFragment")
public class SettingTabFragment extends AbsTabFragment implements SettingTabContract.View {

    private static final String TAG = "SettingTabFragment";

    private Context mContext;
    private SettingTabPresenter mSettingTabPresenter;
    private Button mThemeColor;
    private View mThemeColorBg;
    private View mChangeFont;

    private View mColorUppperTextBg;
    private Button mColorUppperText;
    private View mColorLowerTextBg;
    private Button mColorLowerText;
    private View mColorDayNumberTextBg;
    private Button mColorDayNumberText;
    private View mColorNickName1TextBg;
    private Button mColorNickName1Text;
    private View mColorNickName2TextBg;
    private Button mColorNickName2Text;

    public SettingTabFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mSettingTabPresenter = new SettingTabPresenter(this);
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

    private View.OnClickListener mOnClickListener = v -> {

        switch (v.getId()) {
            case R.id.theme_color_bg:
                mSettingTabPresenter.changeThemeColor(mContext);
                break;
            case R.id.change_font:
                mSettingTabPresenter.changeFont(mContext);
                break;
            default:
                break;
        }
    };

    private void initView(View rootView) {
        mThemeColorBg = rootView.findViewById(R.id.theme_color_bg);
        mThemeColor = (Button) rootView.findViewById(R.id.theme_color);
        mThemeColor.getBackground().setColorFilter(SharedPrefUtil.getInstance(mContext)
                .getInt(Constants.LOVE_THEME_COLOR, Constants.DEFAULT_THEME_COLOR), PorterDuff.Mode.SRC_IN);
        mThemeColorBg.setOnClickListener(mOnClickListener);
        mChangeFont = rootView.findViewById(R.id.change_font);
        mChangeFont.setOnClickListener(mOnClickListener);
    }

    private void initViewTextColorView(View rootView) {

    }

    @Override
    public void onTabSelected() {

    }

    @Override
    public void onTabUnselected() {

    }

    @Override
    public void onChangeThemeColor(int color) {
        mThemeColor.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        SharedPrefUtil.getInstance(mContext).saveInt(Constants.LOVE_THEME_COLOR, color);
        ((MainActivity) mContext).reload();
    }

    @Override
    public void onChangeFont(int fontType) {
        SharedPrefUtil.getInstance(mContext).saveInt(Constants.SETTING_FONT_STYLE, fontType);
        ((MainActivity) mContext).reload();
    }

    @Override
    public void onChangeTextColor(int color, int textType) {

        Log.i(TAG, "onChangeTextColor: color = " + color + ", textType = " + textType);

    }
}
