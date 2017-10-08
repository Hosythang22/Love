package hs.thang.com.love.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaObject;
import hs.thang.com.thu.R;

public class GridImageAdapter extends GalleryBaseAdapter {

    private static final String TAG = "GridImageAdapter";

    private ArrayList<MediaItem> mMediaItems;

    public GridImageAdapter(Context context) {
        super(context);
    }

    public void setMediaItems(ArrayList<MediaItem> mediaItems) {
        mMediaItems = mediaItems;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.all_grid_item, parent, false);
        return new GridImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GridImageViewHolder viewHolder = (GridImageViewHolder) holder;
        if (position < 0 || position >= getItemCount()) {
            Log.e(TAG, "wrong position, position=" + position);
            return;
        }

        MediaItem mediaItem = mMediaItems.get(position);
        viewHolder.bind(mediaItem);
    }

    class GridImageViewHolder extends AbsViewHolder {

        protected final ImageView imageView;

        public GridImageViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
        }

        @Override
        void bind(MediaObject mediaObject) {
            MediaItem mediaItem = (MediaItem) mediaObject;

            RequestOptions options = new RequestOptions()
                    /*.signature(mediaItem.getSignature())*/
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .centerCrop()
                    /*.placeholder(placeholder)*/
                    //.animate(R.anim.fade_in)//TODO:DONT WORK WELL
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            Glide.with(imageView.getContext())
                    .load(mediaItem.getUri())
                    .apply(options)
                    .thumbnail(0.5f)
                    .into(imageView);

            itemView.setOnClickListener(v ->{
                mOnItemClickListener.onItemClick(mediaItem);
            });
        }
    }

    @Override
    public int getItemCount() {
        return (mMediaItems != null) ? mMediaItems.size() : 0;
    }

    public void clear() {
        if (mMediaItems != null) {
            mMediaItems.clear();
            notifyDataSetChanged();
        }
    }
}
