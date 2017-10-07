package hs.thang.com.love.view.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import hs.thang.com.love.common.NewEventDialog;
import hs.thang.com.love.core.Event;
import hs.thang.com.love.data.StringData;
import hs.thang.com.love.gallery.GalleryActivity;
import hs.thang.com.thu.R;

@SuppressLint("ValidFragment")
public class TimeFragment extends AbsFragment {

    private Context mContext;
    private LinearLayout mBackGroundTime;
    private TextView mStartGallery;
    private TextView mCreateNewEvent;

    public TimeFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_time, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mBackGroundTime = (LinearLayout) rootView.findViewById(R.id.backgound_frag_setting);
        mStartGallery = (TextView) rootView.findViewById(R.id.textView3);
        mCreateNewEvent = (TextView) rootView.findViewById(R.id.textView4);

        mStartGallery.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, GalleryActivity.class);
            startActivity(intent);
        });

        mCreateNewEvent.setOnClickListener(v -> {
            CreateNewEvent(mCreateNewEvent, "fuck");
        });
    }

    private void CreateNewEvent(final TextView nickName, final String string) {
        NewEventDialog newEventDialog = new NewEventDialog(mContext);
        newEventDialog.showCreateNewEventDialog();
        newEventDialog.setCurrentName(nickName.getText().toString());
        newEventDialog.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                Event ev = (Event) data;
                String newName = ev.getData() + "";
                StringData.setPreference(mContext, string, newName);
                nickName.setText(newName);
            }
        });
    }
}