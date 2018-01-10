package hs.thang.com.love.eventscreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import hs.thang.com.love.common.Model;
import hs.thang.com.love.common.viewmodel.BaseAdapter;
import hs.thang.com.love.data.EventInformation;
import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaObject;
import hs.thang.com.love.util.TextViewC;
import hs.thang.com.thu.R;

/**
 * Created by DELL on 10/8/2017.
 */

public class EventAdapter extends BaseAdapter {

    private static final String TAG = "EventAdapter";

    private ArrayList<EventInformation> mEventInfors = new ArrayList<>();

    public EventAdapter(Context context) {
        super(context);
    }

    public void setEventInforItem(EventInformation eventInfor) {
        mEventInfors.add(eventInfor);
        notifyDataSetChanged();
    }
    public void setEventInforItems(ArrayList<EventInformation> eventInfors) {
        mEventInfors = eventInfors;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.time_items, parent, false);
        return new TimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TimeViewHolder viewHolder = (TimeViewHolder) holder;
        if (position < 0 || position >= getItemCount()) {
            Log.e(TAG, "wrong position, position=" + position);
            return;
        }

        EventInformation eventInfor = mEventInfors.get(position);
        viewHolder.bind(eventInfor);
    }

    class TimeViewHolder extends AbsViewHolder {

        protected final ImageView imageView;
        protected final TextViewC textInfor;
        protected final TextViewC date;
        protected final TextViewC howLong;

        public TimeViewHolder(View itemView) {
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

    @Override
    public int getItemCount() {
        return (mEventInfors != null) ? mEventInfors.size() : 0;
    }

    public void clear() {
        if (mEventInfors != null) {
            mEventInfors.clear();
            notifyDataSetChanged();
        }
    }
}
