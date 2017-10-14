package hs.thang.com.love;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.TextView;

import hs.thang.com.love.adapter.CustomPagerAdapter;
import hs.thang.com.love.chat.ChatBottomSheetDialogFragment;
import hs.thang.com.love.chat.chathead.ChatHeadService;
import hs.thang.com.love.chat.chathead.Utils;
import hs.thang.com.love.chat.utils.Constants;
import hs.thang.com.love.util.Color;
import hs.thang.com.love.util.LoveUtil;
import hs.thang.com.love.view.tab.CustomTablayout;
import hs.thang.com.love.view.tab.MainFragment;
import hs.thang.com.thu.R;

public class MainActivity extends AbsActivity implements MainFragment.UpdateBackgroundListener {

    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;

    private ViewPager mViewPager;
    private CustomTablayout mTabLayout;
    public LinearLayout mLinearLayout;

    private ChatHeadService chatHeadService;
    private boolean bound;

    // for test
    private TextView mTest;
    private static final String TAG = "MainActivity";

    int[] pagerColors = {R.color.transparent_40, R.color.transparent_20, R.color.transparent_40};

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

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ChatHeadService.LocalBinder binder = (ChatHeadService.LocalBinder) service;
            chatHeadService = binder.getService();
            bound = true;
            chatHeadService.minimize();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    private void startChatHead() {
        Intent intent = new Intent(this, ChatHeadService.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout) findViewById(R.id.backgound);

        mTabLayout = (CustomTablayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.transparent_20));

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        CustomPagerAdapter adapter = new CustomPagerAdapter(this, getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.setAdapter(adapter);
        mTabLayout.setBackgroundColor(Color.TRANSPARENT_40);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        mTabLayout.setBackgroundColor(Color.TRANSPARENT_40);
                        break;
                    case 1:
                        mTabLayout.setBackgroundColor(Color.TRANSPARENT_20);
                        break;
                    case 2:
                        mTabLayout.setBackgroundColor(Color.TRANSPARENT_40);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
        mTabLayout.getTabAt(1).select();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            final ChatBottomSheetDialogFragment myBottomSheet = ChatBottomSheetDialogFragment.newInstance(
                    getIntent().getExtras().getString(Constants.ARG_RECEIVER),
                    getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID),
                    getIntent().getExtras().getString(Constants.ARG_FIREBASE_TOKEN));
            myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
        });

        if(Utils.canDrawOverlays(this))
            startChatHead();
        else{
            requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
        }
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
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void updateBackground(Uri uri) {
        Bitmap bitmap = LoveUtil.decodeUriToBitmap(this, uri);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        mLinearLayout.setBackground(drawable);
    }

    private void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(this)) {
                needPermissionDialog(requestCode);
            }else{
                startChatHead();
            }
        }
    }

    private void needPermissionDialog(final int requestCode){
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
}
