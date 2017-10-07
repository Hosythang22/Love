package hs.thang.com.love.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import hs.thang.com.love.gallery.data.MediaObject;

public abstract class GalleryBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected ArrayList<MediaObject> mMediaObjects;
    protected final Context mContext;

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


    public GalleryBaseAdapter(Context context, ArrayList<MediaObject> mediaObjects) {
        mMediaObjects = mediaObjects;
        mContext = context;
    }


    @Override
    public int getItemCount() {
        return mMediaObjects.size();
    }

    /*public void clear() {
        mMediaObjects.clear();
        notifyDataSetChanged();
    }*/
}
