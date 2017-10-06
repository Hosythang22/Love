package hs.thang.com.love.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaObject;
import hs.thang.com.thu.R;

public class GridImageAdapter extends GalleryBaseAdapter {

    private static final String TAG = "GridImageAdapter";

    private ArrayList<MediaItem> mMediaItems;

    /*public GridImageAdapter(Context context, ArrayList<MediaObject> mediaObjects) {
        super(context, mediaObjects);
    }*/
    public GridImageAdapter(Context context, ArrayList<MediaItem> mediaItems) {
        super(context, null);
        mMediaItems = mediaItems;
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
            /*Picasso.with(mContext).load(((MediaItem) mediaItem).getThumbnail())
                    .error(R.drawable.ic_action_fingerprint)
                    .placeholder(R.drawable.avata_circle)
                    .into(imageView);
            //Setting text view title
            textView.setText(Html.fromHtml(((MediaItem) mediaItem).getTitle()));*/
            MediaItem mediaItem = (MediaItem) mediaObject;
            Glide.with(imageView.getContext())
                    .load(mediaItem.getUri())
                        /*.apply(options)*/
                    .thumbnail(0.5f)
                    .into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mMediaItems.size();
    }
}
