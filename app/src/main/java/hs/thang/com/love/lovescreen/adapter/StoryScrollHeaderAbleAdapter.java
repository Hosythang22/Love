package hs.thang.com.love.lovescreen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import hs.thang.com.love.common.Model;
import hs.thang.com.love.common.viewmodel.ScrollHeaderAbleAdapter;
import hs.thang.com.love.data.EventInformation;
import hs.thang.com.love.util.TextViewC;
import hs.thang.com.thu.R;

/**
 * Created by sev_user on 1/5/2018.
 */

public class StoryScrollHeaderAbleAdapter extends ScrollHeaderAbleAdapter {

    private static final String TAG = "StoryScrollHeaderAbleAdapter";

    public StoryScrollHeaderAbleAdapter(Context context, View headerView) {
        super(context, headerView);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final StoryViewHolder viewHolder = (StoryViewHolder) holder;
        if (position < 0 || position >= getItemCount()) {
            Log.e(TAG, "wrong position, position=" + position);
            return;
        }

        EventInformation eventInfor = (EventInformation) mItems.get(position);
        viewHolder.bind(eventInfor);
    }

    public class StoryViewHolder extends ContentViewHolder {

        protected final ImageView imageView;
        protected final TextViewC textInfor;
        protected final TextViewC date;
        protected final TextViewC howLong;

        public StoryViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            textInfor = (TextViewC) itemView.findViewById(R.id.event_infor);
            date = (TextViewC) itemView.findViewById(R.id.date);
            howLong = (TextViewC) itemView.findViewById(R.id.how_long);
        }

        @Override
        public void bind(Model model) {
            EventInformation eventInfor = (EventInformation) model;

            textInfor.setText(eventInfor.getmContent());
            date.setText(eventInfor.getmDate());
            howLong.setText("how long");

            RequestOptions options = new RequestOptions()
                    /*.signature(mediaItem.getSignature())*/
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .centerCrop()
                    /*.placeholder(placeholder)*/
                    //.animate(R.anim.fade_in)//TODO:DONT WORK WELL
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            Glide.with(imageView.getContext())
                    .load(eventInfor.getmUri())
                    .apply(options)
                    .thumbnail(0.5f)
                    .into(imageView);

            /*itemView.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(eventInfor);
            });*/
        }
    }
}
