package hs.thang.com.love;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipkart.circularImageView.CircularDrawable;
import com.flipkart.circularImageView.TextDrawer;
import com.flipkart.circularImageView.notification.CircularNotificationDrawer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import hs.thang.com.love.adapter.CustomPagerAdapter;
import hs.thang.com.love.chat.ChatBottomSheetDialogFragment;
import hs.thang.com.love.chat.chathead.ChatHeadService;
import hs.thang.com.love.chat.chathead.FloatingActivity;
import hs.thang.com.love.chat.chathead.Utils;
import hs.thang.com.love.chat.chathead.lib.ui.ChatHead;
import hs.thang.com.love.chat.chathead.lib.ui.ChatHeadViewAdapter;
import hs.thang.com.love.chat.chathead.lib.ui.MinimizedArrangement;
import hs.thang.com.love.chat.chathead.lib.ui.container.DefaultChatHeadManager;
import hs.thang.com.love.chat.chathead.lib.ui.container.WindowManagerContainer;
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

    //private final IBinder mBinder = new ChatHeadService.LocalBinder();
    private DefaultChatHeadManager<String> chatHeadManager;
    private int chatHeadIdentifier = 0;
    private WindowManagerContainer windowManagerContainer;
    private Map<String, View> viewCache = new HashMap<>();

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

    /*private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ChatHeadService.LocalBinder binder = (ChatHeadService.LocalBinder) service;
            chatHeadService = binder.getService();
            chatHeadService.minimize();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };*/

    /*private void startChatHead() {
        Intent intent = new Intent(this, ChatHeadService.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }*/

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

        /*if(Utils.canDrawOverlays(this))
            startChatHead();
        else{
            requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
        }*/
        windowManagerContainer = new WindowManagerContainer(this);
        chatHeadManager = new DefaultChatHeadManager<String>(this, windowManagerContainer);
        chatHeadManager.setViewAdapter(new ChatHeadViewAdapter<String>() {

            @Override
            public View attachView(String key, ChatHead chatHead, ViewGroup parent) {
                View cachedView = viewCache.get(key);
                if (cachedView == null) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.fragment_test, parent, false);
                    TextView identifier = (TextView) view.findViewById(R.id.identifier);
                    identifier.setText(key);
                    cachedView = view;
                    viewCache.put(key, view);
                }
                parent.addView(cachedView);
                return cachedView;
            }

            @Override
            public void detachView(String key, ChatHead<? extends Serializable> chatHead, ViewGroup parent) {
                View cachedView = viewCache.get(key);
                if (cachedView != null) {
                    parent.removeView(cachedView);
                }
            }

            @Override
            public void removeView(String key, ChatHead<? extends Serializable> chatHead, ViewGroup parent) {
                View cachedView = viewCache.get(key);
                if (cachedView != null) {
                    viewCache.remove(key);
                    parent.removeView(cachedView);
                }
            }

            @Override
            public Drawable getChatHeadDrawable(String key) {
                //return this.getChatHeadDrawable(key);
                return null;
            }
        });

        addChatHead();
        chatHeadManager.setArrangement(MinimizedArrangement.class, null);
        //moveToForeground();
    }

    public void minimize() {
        chatHeadManager.setArrangement(MinimizedArrangement.class, null);
    }

    private void moveToForeground() {
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("Springy heads")
                .setContentText("Click to configure.")
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, FloatingActivity.class), 0))
                .build();

        //startForeground(1, notification);
    }

    private Drawable getChatHeadDrawable(String key) {
        Random rnd = new Random();
        int randomColor = android.graphics.Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        CircularDrawable circularDrawable = new CircularDrawable();
        circularDrawable.setBitmapOrTextOrIcon(new TextDrawer().setText("C" + key).setBackgroundColor(randomColor));
        int badgeCount = (int) (Math.random() * 10f);
        circularDrawable.setNotificationDrawer(new CircularNotificationDrawer().setNotificationText(String.valueOf(badgeCount)).setNotificationAngle(135).setNotificationColor(android.graphics.Color.WHITE, android.graphics.Color.RED));
        circularDrawable.setBorder(android.graphics.Color.WHITE, 3);
        return circularDrawable;
    }

    public void addChatHead() {
        chatHeadIdentifier++;
        chatHeadManager.addChatHead(String.valueOf(chatHeadIdentifier), false, true);
        chatHeadManager.bringToFront(chatHeadManager.findChatHeadByKey(String.valueOf(chatHeadIdentifier)));
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
        /*if (mConnection != null) {
            unbindService(mConnection);
        }*/
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
