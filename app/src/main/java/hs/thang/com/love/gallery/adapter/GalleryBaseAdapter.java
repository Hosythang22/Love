package hs.thang.com.love.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaObject;
import hs.thang.com.love.gallery.data.MediaSet;

public abstract class GalleryBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final Context mContext;


    protected OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(MediaItem mediaSet);

        void onItemLongClick(MediaItem mediaSet);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    abstract class AbsViewHolder extends RecyclerView.ViewHolder {

        public AbsViewHolder(View itemView) {
            super(itemView);
        }

        abstract void bind(MediaObject mediaObject);
    }



    void onResume() {

    }

    void onPause() {

    }


    public GalleryBaseAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getItemCount() {
        return -1;
    }

    /*public void clear() {
        mMediaObjects.clear();
        notifyDataSetChanged();
    }*/
}
