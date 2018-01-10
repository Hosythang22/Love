package hs.thang.com.love.common.viewmodel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import hs.thang.com.love.common.Model;
import hs.thang.com.love.data.EventInformation;
import hs.thang.com.love.util.TextViewC;
import hs.thang.com.thu.R;

/**
 * Created by sev_user on 1/5/2018.
 */

public class ScrollHeaderAbleAdapter extends BaseAdapter {

    private static final String TAG = "ScrollHeaderAbleAdapter";
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    public ArrayList<EventInformation> mItems;
    private View mHeaderView;

    public ScrollHeaderAbleAdapter(Context context, View headerView) {
        super(context);
        mHeaderView = headerView;
    }

    public void setEventInforItem(EventInformation eventInfor) {
        mItems.add(eventInfor);
        notifyDataSetChanged();
    }

    public void setEventInforItems(ArrayList<EventInformation> eventInfors) {
        mItems = eventInfors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mItems.size();
        } else {
            return (mItems != null) ? (mItems.size() + 1) : 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else {
            /*return new ItemViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, parent, false));*/
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.time_items, parent, false);
            return new ContentViewHolder(itemView);
            //return new ContentViewHolder(mInflater.inflate(mLayoutId, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > getItemCount()) {
            Log.e(TAG, "wrong position, position=" + position);
            return;
        }
        if (holder instanceof ContentViewHolder) {
            final ContentViewHolder viewHolder = (ContentViewHolder) holder;
            EventInformation eventInfor = (EventInformation) mItems.get(position - 1);
            viewHolder.bind(eventInfor);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    public class ContentViewHolder extends AbsViewHolder {
        protected final ImageView imageView;
        protected final TextViewC textInfor;
        protected final TextViewC date;
        protected final TextViewC howLong;

        public ContentViewHolder(View itemView) {
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

        }
    }
}
