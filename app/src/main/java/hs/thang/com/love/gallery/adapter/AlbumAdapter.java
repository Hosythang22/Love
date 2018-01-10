package hs.thang.com.love.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hs.thang.com.love.common.Model;
import hs.thang.com.love.common.viewmodel.BaseAdapter;
import hs.thang.com.love.gallery.data.MediaSet;
import hs.thang.com.love.R;

public class AlbumAdapter extends BaseAdapter {

    private static final String TAG = "AlbumAdapter";

    private ArrayList<MediaSet> mMediaSets;

    public AlbumAdapter(Context context, ArrayList<MediaSet> mediaSets) {
        super(context);
        mMediaSets = mediaSets;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final AlbumAdapter.AlbumAdapterViewHolder viewHolder = (AlbumAdapter.AlbumAdapterViewHolder) holder;
        if (position < 0 || position >= getItemCount()) {
            Log.e(TAG, "wrong position, position=" + position);
            return;
        }

        MediaSet mediaSet = mMediaSets.get(position);
        viewHolder.bind(mediaSet);
    }

    class AlbumAdapterViewHolder extends AbsViewHolder {
        protected final ImageView imageView;
        protected final TextView albumName;
        protected final TextView albumCount;

        public AlbumAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            albumName = (TextView) itemView.findViewById(R.id.album_name);
            albumCount = (TextView) itemView.findViewById(R.id.numberOfImage);
        }

        @Override
        public void bind(Model mediaObject) {

        }
    }

    @Override
    public int getItemCount() {
        return mMediaSets.size();
    }
}
