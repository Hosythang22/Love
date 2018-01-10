package hs.thang.com.love.chatscreen.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import hs.thang.com.love.MainActivity;
import hs.thang.com.love.chatscreen.chat.ui.activities.LoginActivity;
import hs.thang.com.love.chatscreen.chat.ui.activities.UserListingActivity;
import hs.thang.com.love.common.activity.AbsTabFragment;
import hs.thang.com.love.R;

/**
 * Created by sev_user on 1/5/2018.
 */

@SuppressLint("ValidFragment")
public class ChatTabFragment extends AbsTabFragment {

    private static final String TAG = "ChatTabFragment";

    private Context mContext;
    private Activity mActivity;
    private static final int SPLASH_TIME_MS = 1000;
    private Handler mHandler;
    private Runnable mRunnable;

    public ChatTabFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_splash, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();
        if(mActivity instanceof MainActivity) {
            ((MainActivity) mActivity).setChatTabListener(this);
        }
    }

    private void initView(View rootView) {
    }

    @Override
    public void onTabSelected() {
        Log.e(TAG, "onTabSelected: " );
        mRunnable = () -> {
            // check if user is already logged in or not
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                // if logged in redirect the user to user listing activity
/*                UsersFragment usersFragment = UsersFragment.newInstance(UsersFragment.TYPE_ALL);
                mActivity.getFragmentManager()
                        .beginTransaction().replace(R.id.frame_layout_content_register, usersFragment)
                        .addToBackStack(null)
                        .commit();*/
                UserListingActivity.startActivity(mContext);
            } else {
                // otherwise redirect the user to login activity
                LoginActivity.startIntent(mContext);
            }
            //finish();
        };

        mHandler.postDelayed(mRunnable, SPLASH_TIME_MS);
    }

    @Override
    public void onTabUnselected() {

    }
}
