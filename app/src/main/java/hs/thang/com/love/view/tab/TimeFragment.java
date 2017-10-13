package hs.thang.com.love.view.tab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hs.thang.com.love.chat.ui.activities.SplashActivity;
import hs.thang.com.love.common.NewEventDialog;
import hs.thang.com.love.core.Event;
import hs.thang.com.love.data.EventInfor;
import hs.thang.com.love.gallery.GalleryActivity;
import hs.thang.com.love.gallery.adapter.DividerItemDecoration;
import hs.thang.com.love.gallery.adapter.TimeAdapter;
import hs.thang.com.love.provider.ProviderUtil;
import hs.thang.com.thu.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

@SuppressLint("ValidFragment")
public class TimeFragment extends AbsFragment implements NewEventDialog.OnItemClickListener {

    public static final int REQUEST_PICK_IMAGE = 1004;
    public static final String TAG = "TimeFragment";

    private Context mContext;
    private LinearLayout mBackGroundTime;
    private TextView mCreateNewEvent;
    private TextView mStartChatActivity;
    private NewEventDialog mNewEventDialog;

    private RecyclerView mRecyclerView;
    private TimeAdapter mTimeAdapter;

    public TimeFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View rootView = inflater.inflate(R.layout.fragment_time, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mBackGroundTime = (LinearLayout) rootView.findViewById(R.id.backgound_frag_setting);
        mCreateNewEvent = (TextView) rootView.findViewById(R.id.textView4);
        mStartChatActivity = (TextView) rootView.findViewById(R.id.textView3);

        initRecycleView(rootView);

        mCreateNewEvent.setOnClickListener(v -> {
            CreateNewEvent(mCreateNewEvent, "fuck");
        });

        mStartChatActivity.setOnClickListener(v -> {
            startChatActivity();
        });

        initData();
    }

    private void startChatActivity() {
        Intent intent = new Intent(mContext, SplashActivity.class);
        startActivity(intent);
    }

    private void initData() {
        ArrayList<EventInfor> eventInfors = new ArrayList<>();
        ProviderUtil.getEventInfor(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventInfor -> {
                    eventInfors.add(eventInfor);
                }, throwable -> {
                    Log.i(TAG, "initData: throwable = " + throwable);
                }, () -> {
                    mTimeAdapter.setEventInforItems(eventInfors);
                });
    }

    private void initRecycleView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new LandingAnimator(new OvershootInterpolator(1f)));
        mTimeAdapter = new TimeAdapter(mContext);
        mRecyclerView.setAdapter(mTimeAdapter);
    }

    private void CreateNewEvent(final TextView nickName, final String string) {
        mNewEventDialog = new NewEventDialog(mContext);
        mNewEventDialog.showCreateNewEventDialog();
        mNewEventDialog.setCurrentName(nickName.getText().toString());
        mNewEventDialog.setOnItemClickListener(this);

        mNewEventDialog.addObserver((observable, data) -> {
            Event ev = (Event) data;
            EventInfor eventInfor = (EventInfor) ev.getData();
            ProviderUtil.insertEvents(mContext, eventInfor);
            mTimeAdapter.setEventInforItem(eventInfor);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    Uri uri = data.getData();
                    mNewEventDialog.setThumbnail(uri);
                    Log.i(TAG, "onActivityResult: data = " + data + ", uri =" + uri);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onItemClick(int id) {
        switch (id) {
            case NewEventDialog.PICK_IMAGE:
                Intent pickImageIntent = new Intent(mContext, GalleryActivity.class);
                startActivityForResult(pickImageIntent, TimeFragment.REQUEST_PICK_IMAGE);
                break;
            /*case NewEventDialog.PICK_DATE:
                Intent pickImageIntent = new Intent(mContext, GalleryActivity.class);
                startActivityForResult(pickImageIntent, TimeFragment.REQUEST_PICK_IMAGE);
                break;*/

        }

    }
}
