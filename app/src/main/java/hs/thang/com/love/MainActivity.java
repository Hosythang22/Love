package hs.thang.com.love;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import hs.thang.com.love.common.activity.TabPackageListener;
import hs.thang.com.love.common.viewmodel.LovePackagePagerAdapter;
import hs.thang.com.love.chatscreen.chat.utils.Constants;
import hs.thang.com.love.common.viewmodel.LoveViewPager;
import hs.thang.com.love.util.LoveUtil;
import hs.thang.com.love.common.view.LoveTabLayout;
import hs.thang.com.love.lovescreen.viewmodel.LoveTabFragment;

public class MainActivity extends AbsActivity
        implements LoveTabFragment.UpdateBackgroundListener, LoveTabLayout.OnTabSelectedListener,
        ViewPager.OnPageChangeListener {

    private LoveTabLayout.TabLayoutOnPageChangeListener mTabLayoutOnPageChangeListener;
    private LoveViewPager mViewPager;
    private LoveTabLayout mTabLayout;
    public LinearLayout mLinearLayout;
    private CoordinatorLayout mMainContent;

    public static int sSelectedTab = 1;
    public static ArrayList<Integer> sTabIndex;
    public static final int CHAT_TAB_INDEX = 0;
    public static final int LOVE_TAB_INDEX = 1;
    public static final int SETTING_TAB_INDEX = 2;

    private TabPackageListener mChatTabListener;
    private TabPackageListener mLoveTabListener;
    private TabPackageListener mSettingTabListener;

    // for test
    private TextView mTest;
    private static final String TAG = "CropActivity";

    public static void startActivity(Context context,
                                     String receiver,
                                     String receiverUid,
                                     String firebaseToken) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER, receiver);
        intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUid);
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout) findViewById(R.id.transparent_background);
        mMainContent = (CoordinatorLayout) findViewById(R.id.main_content);
        createTabs();
    }

    public void setChatTabListener(TabPackageListener eventListener) {
        mChatTabListener = eventListener;
    }

    private void createTabs() {
        mTabLayout = (LoveTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (LoveViewPager) findViewById(R.id.viewpager);
        LovePackagePagerAdapter pagerAdapter = new LovePackagePagerAdapter(this, getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayoutOnPageChangeListener = new LoveTabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        mViewPager.addOnPageChangeListener(mTabLayoutOnPageChangeListener);
        mViewPager.addOnPageChangeListener(this);

        //mTabLayout.setAdapter(pagerAdapter);
        mTabLayout.setOnTabSelectedListener(this);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.getTabAt(sSelectedTab).select();

        sTabIndex = new ArrayList<>(Arrays.asList(CHAT_TAB_INDEX, LOVE_TAB_INDEX,
                SETTING_TAB_INDEX));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
        switch (tab.getPosition()) {
            case CHAT_TAB_INDEX:
                mLinearLayout.setBackgroundColor(getResources().getColor(R.color.transparent_45));
                if (mChatTabListener != null) {
                    mChatTabListener.onTabSelected();
                }
                break;
            case LOVE_TAB_INDEX:
                mLinearLayout.setBackgroundColor(getResources().getColor(R.color.transparent_10));
                break;
            case SETTING_TAB_INDEX:
                mLinearLayout.setBackgroundColor(getResources().getColor(R.color.transparent_45));
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //if android version is lollipop or greater, change the color of status bar as well
                /*if (Build.VERSION.SDK_INT >= 21) {
                    // set the color change animation only when page change is from 0 to 1 and from 1 to 2
                    if (position < 2) {
                        getWindow().setStatusBarColor((Integer) new ArgbEvaluator().evaluate(positionOffset, ContextCompat.getColor(getBaseContext(), pagerColors[position]),
                                ContextCompat.getColor(getBaseContext(), pagerColors[position + 1])));
                    } else {
                        getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), pagerColors[position]));
                    }

                }*/

        // set the color change animation only when page change is from 0 to 1 and from 1 to 2
                /*if (position < 2) {
                    mLinearLayout.setBackgroundColor((Integer) new ArgbEvaluator().evaluate(positionOffset, ContextCompat.getColor(getBaseContext(), pagerColors[position]),
                            ContextCompat.getColor(getBaseContext(), pagerColors[position + 1])));
                } else {
                    mLinearLayout.setBackgroundColor(ContextCompat.getColor(getBaseContext(), pagerColors[position]));
                }*/
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mChatTabListener != null) {
            mChatTabListener = null;
        }

        if (mTabLayout != null) {
            mTabLayout.removeAllTabs();
            mTabLayout.setOnTabSelectedListener(this);
            mTabLayout = null;
        }

        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
            //mViewPager.setAdapter(null);
            if (mTabLayoutOnPageChangeListener != null) {
                mViewPager.removeOnPageChangeListener(mTabLayoutOnPageChangeListener);
                mTabLayoutOnPageChangeListener = null;
            }
            mViewPager = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void updateBackground(Uri uri) {
        Bitmap bitmap = LoveUtil.decodeUriToBitmap(this, uri);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        mMainContent.setBackground(drawable);
    }

    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(this)) {
                needPermissionDialog(requestCode);
            }else{
                startChatHead();
            }
        }
    }*/

    private void needPermissionDialog(final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                (dialog, which) -> {
                    // TODO Auto-generated method stub
                    requestPermission(requestCode);
                });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // TODO Auto-generated method stub

        });
        builder.setCancelable(false);
        builder.show();
    }

    public void reload() {
        if (Build.VERSION.SDK_INT >= 11) {
            Intent intent = new Intent();
            intent.setClass(this, this.getClass());
            this.startActivity(intent);
            this.finish();
        } else {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);

            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}
