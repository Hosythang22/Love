package hs.thang.com.love.gallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import hs.thang.com.love.gallery.data.MediaSet;
import hs.thang.com.love.R;

import java.util.List;

public class AlbumSpinnerAdapter extends ArrayAdapter<MediaSet> {
    private Context mContext;

    List<MediaSet> mMediaSets = null;

    public AlbumSpinnerAdapter(Context context, List<MediaSet> mediaSets) {
        super(context, R.layout.spinner_selected_item, mediaSets);
        this.mContext = context;
        this.mMediaSets = mediaSets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.spinner_selected_item, null);
        }
        ((TextView) convertView.findViewById(R.id.texto)).setText(mMediaSets.get(position).getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.album_item, parent, false);
        }

        if (row.getTag() == null) {
            ViewHolder redSocialHolder = new ViewHolder();
            redSocialHolder.thumbnail = ((ImageView) row.findViewById(R.id.thumbnail));
            redSocialHolder.textView = ((TextView) row.findViewById(R.id.album_name));
            redSocialHolder.numberImage = ((TextView) row.findViewById(R.id.numberOfImage));
            row.setTag(redSocialHolder);
        }

        MediaSet mediaset = mMediaSets.get(position);
        Glide.with(((ViewHolder) row.getTag()).thumbnail.getContext())
                .load(mediaset.getUri())
                /*.apply(options)*/
                .thumbnail(0.5f)
                .into(((ViewHolder) row.getTag()).thumbnail);
        ((ViewHolder) row.getTag()).textView.setText(mediaset.getName());
        ((ViewHolder) row.getTag()).numberImage.setText(String.valueOf(mediaset.getmNumberOfImage()));

        return row;
    }
    private static class ViewHolder {
        ImageView thumbnail;
        TextView textView;
        TextView numberImage;
    }
}

