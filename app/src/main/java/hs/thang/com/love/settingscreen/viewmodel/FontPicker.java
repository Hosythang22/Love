package hs.thang.com.love.settingscreen.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import hs.thang.com.love.gallery.adapter.DividerItemDecoration;
import hs.thang.com.love.settingscreen.adapter.FontPickerAdapter;
import hs.thang.com.love.settingscreen.view.FontPickerDialog;
import hs.thang.com.thu.R;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * Created by sev_user on 1/10/2018.
 */

public class FontPicker {

    private static final String TAG = "FontPicker";
    private ArrayList<Integer> mFontIds;
    private FontPickerAdapter mAdapter;
    private WeakReference<Activity> mContext;
    private RecyclerView mRecyclerView;
    private View mDialogView;
    private WeakReference<FontPickerDialog> mDialog;

    private OnChooseFontListener mOnChooseFontListener;

    public interface OnChooseFontListener {
        void onChooseFont(int position, int font);

        void onCancel();
    }

    public FontPicker setOnChooseFontListener(OnChooseFontListener listener) {
        mOnChooseFontListener = listener;
        return this;
    }

    public FontPicker(Activity context) {
        mContext = new WeakReference<Activity>(context);
        mDialogView = LayoutInflater.from(context).inflate(R.layout.font_list, null, false);

        createFontIds();
    }

    private void createFontIds() {
        mFontIds = new ArrayList<>();
        for (int i = 1; i < 23; i++) {
            mFontIds.add(i);
        }
    }

    public void show() {
        if (mContext == null) {
            return;
        }

        Activity context = mContext.get();
        if (context == null) {
            return;
        }

        if (mFontIds == null || mFontIds.isEmpty()) {
            createFontIds();
        }

        mDialog = new WeakReference<>(new FontPickerDialog(context, mDialogView));
        initRycycleView(context);

        FontPickerDialog dialog = mDialog.get();
        if (dialog != null && !context.isFinishing()) {
            showMediaRenameDialog(dialog, context);
        }
    }

    public void showMediaRenameDialog(FontPickerDialog dialog, Activity context) {
        try {
            dialog.show(context.getFragmentManager(), "dialog");
        } catch (IllegalStateException e) {
            android.util.Log.e(TAG, e.toString());
        }
    }

    private void initRycycleView(Context context) {
        mRecyclerView = (RecyclerView) mDialogView.findViewById(R.id.font_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new LandingAnimator(new OvershootInterpolator(1f)));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new FontPickerAdapter(context, mFontIds, mDialog, mOnChooseFontListener);
        mRecyclerView.setAdapter(mAdapter);
    }
}
