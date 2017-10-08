package hs.thang.com.love.view.tab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

import static android.content.ContentValues.TAG;

@SuppressLint("ValidFragment")
public class TimeFragment extends AbsFragment implements NewEventDialog.OnItemClickListener {

    public static final int REQUEST_PICK_IMAGE = 1004;
    public static final String TAG = "TimeFragment";

    private Context mContext;
    private LinearLayout mBackGroundTime;
    private TextView mStartGallery;
    private TextView mCreateNewEvent;
    private NewEventDialog mNewEventDialog;

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
            /*Intent intent = new Intent(mContext, GalleryActivity.class);
            startActivity(intent);*/

            Intent pickImageIntent = new Intent(mContext, GalleryActivity.class);
            startActivityForResult(pickImageIntent, TimeFragment.REQUEST_PICK_IMAGE);
        });

        mCreateNewEvent.setOnClickListener(v -> {
            CreateNewEvent(mCreateNewEvent, "fuck");
        });
    }

    private void CreateNewEvent(final TextView nickName, final String string) {
        mNewEventDialog = new NewEventDialog(mContext);
        mNewEventDialog.showCreateNewEventDialog();
        mNewEventDialog.setCurrentName(nickName.getText().toString());
        mNewEventDialog.setOnItemClickListener(this);

        mNewEventDialog.addObserver((observable, data) -> {
            Event ev = (Event) data;
            String newName = ev.getData() + "";
            StringData.setPreference(mContext, string, newName);
            nickName.setText(newName);
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
    public void onItemClick() {
        Intent pickImageIntent = new Intent(mContext, GalleryActivity.class);
        startActivityForResult(pickImageIntent, TimeFragment.REQUEST_PICK_IMAGE);
    }
}
