package hs.thang.com.love.gallery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaObject;
import hs.thang.com.love.gallery.data.MediaSet;
import hs.thang.com.love.util.Color;
import hs.thang.com.thu.R;

public class AlbumAdapter extends ArrayAdapter<MediaSet> {

    private static final String TAG = "AlbumAdapter";

    private ArrayList<MediaSet> mMediaSets;
    private Context mContext;

    public AlbumAdapter(Context context, ArrayList<MediaSet> mediaSets) {
        super(context, 0, mediaSets);
        mContext = context;
        mMediaSets = mediaSets;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MediaSet mediaSet = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.album_item, parent, false);
            viewHolder.albumName = (TextView) convertView.findViewById(R.id.album_name);
            viewHolder.numberImage = (TextView) convertView.findViewById(R.id.numberOfImage);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        if (position == 0) {
            viewHolder.albumName.setText("Fuck Off");
            viewHolder.albumName.setTextColor(Color.BLACK);
        }
        viewHolder.albumName.setText(mediaSet.getmAlbumName());
        viewHolder.numberImage.setText(String.valueOf(mediaSet.getmNumberOfImage()));
        Glide.with(viewHolder.imageView.getContext())
                .load(mediaSet.getUri())
                        /*.apply(options)*/
                .thumbnail(0.5f)
                .into(viewHolder.imageView);
        // Return the completed view to render on screen

        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private static class ViewHolder {
        TextView albumName;
        TextView numberImage;
        ImageView imageView;
    }
}
