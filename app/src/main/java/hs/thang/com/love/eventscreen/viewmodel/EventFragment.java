package hs.thang.com.love.eventscreen.viewmodel;

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

import hs.thang.com.love.chatscreen.chat.ui.activities.SplashActivity;
import hs.thang.com.love.common.activity.AbsTabFragment;
import hs.thang.com.love.common.dialog.NewEventDialog;
import hs.thang.com.love.common.activity.AbsFragment;
import hs.thang.com.love.common.comunication.Event;
import hs.thang.com.love.common.viewmodel.ScrollHeaderAbleAdapter;
import hs.thang.com.love.data.EventInformation;
import hs.thang.com.love.gallery.GalleryActivity;
import hs.thang.com.love.gallery.adapter.DividerItemDecoration;
import hs.thang.com.love.eventscreen.adapter.EventAdapter;
import hs.thang.com.love.common.provider.ProviderUtil;
import hs.thang.com.thu.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

@SuppressLint("ValidFragment")
public class EventFragment extends AbsTabFragment implements NewEventDialog.OnItemClickListener {

    public static final int REQUEST_PICK_IMAGE = 1004;
    public static final String TAG = "EventFragment";

    private Context mContext;
    private TextView mCreateNewEvent;
    private TextView mStartChatActivity;
    private NewEventDialog mNewEventDialog;

    private RecyclerView mRecyclerView;
    private EventAdapter mEventAdapter;
    private ScrollHeaderAbleAdapter mScrollAdapter;

    public EventFragment(Context context) {
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
       // mCreateNewEvent = (TextView) rootView.findViewById(R.id.textView4);
        //mStartChatActivity = (TextView) rootView.findViewById(R.id.textView3);

        initRecycleView(rootView);

        mCreateNewEvent.setOnClickListener(v -> {
            CreateNewEvent(mCreateNewEvent, "fuck");
        });

        /*mStartChatActivity.setOnClickListener(v -> {
            startChatActivity();
        });*/

        initData();
    }

    private void startChatActivity() {
        Intent intent = new Intent(mContext, SplashActivity.class);
        startActivity(intent);
    }

    private void initData() {
        ArrayList<EventInformation> eventInfors = new ArrayList<>();
        ProviderUtil.getEventInfor(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventInfor -> {
                    eventInfors.add(eventInfor);
                }, throwable -> {
                    Log.i(TAG, "initData: throwable = " + throwable);
                }, () -> {
                    mEventAdapter.setEventInforItems(eventInfors);
                });
    }

    private void initRecycleView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new LandingAnimator(new OvershootInterpolator(1f)));
        mEventAdapter = new EventAdapter(mContext);
        mRecyclerView.setAdapter(mEventAdapter);
    }

    private void CreateNewEvent(final TextView nickName, final String string) {
        mNewEventDialog = new NewEventDialog(mContext);
        mNewEventDialog.showCreateNewEventDialog();
        mNewEventDialog.setCurrentName(nickName.getText().toString());
        mNewEventDialog.setOnItemClickListener(this);

        mNewEventDialog.addObserver((observable, data) -> {
            Event ev = (Event) data;
            EventInformation eventInfor = (EventInformation) ev.getData();
            ProviderUtil.insertEvents(mContext, eventInfor);
            mEventAdapter.setEventInforItem(eventInfor);
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
                startActivityForResult(pickImageIntent, EventFragment.REQUEST_PICK_IMAGE);
                break;
            /*case NewEventDialog.PICK_DATE:
                Intent pickImageIntent = new Intent(mContext, GalleryActivity.class);
                startActivityForResult(pickImageIntent, EventFragment.REQUEST_PICK_IMAGE);
                break;*/

        }

    }

    @Override
    public void onTabSelected() {

    }

    @Override
    public void onTabUnselected() {

    }
}
