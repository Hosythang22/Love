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

import hs.thang.com.love.gallery.GalleryActivity;
import hs.thang.com.thu.R;

@SuppressLint("ValidFragment")
public class TimeFragment extends AbsFragment {

    private Context mContext;
    private LinearLayout mBackGroundTime;
    private TextView mStartGallery;

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

        mStartGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GalleryActivity.class);
                startActivity(intent);
            }
        });

    }
}
