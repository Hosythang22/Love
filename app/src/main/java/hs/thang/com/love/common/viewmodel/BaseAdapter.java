package hs.thang.com.love.common.viewmodel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import hs.thang.com.love.common.Model;
import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaObject;

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final Context mContext;


    protected OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(MediaItem mediaSet);

        void onItemLongClick(MediaItem mediaSet);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public abstract class AbsViewHolder extends RecyclerView.ViewHolder {

        public AbsViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(Model model);
    }



    void onResume() {

    }

    void onPause() {

    }


    public BaseAdapter(Context context) {
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
