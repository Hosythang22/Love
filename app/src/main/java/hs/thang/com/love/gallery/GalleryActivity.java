package hs.thang.com.love.gallery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import hs.thang.com.love.gallery.adapter.AlbumSpinnerAdapter;

import hs.thang.com.love.AbsActivity;
import hs.thang.com.love.common.viewmodel.BaseAdapter;
import hs.thang.com.love.gallery.adapter.GridImageAdapter;
import hs.thang.com.love.gallery.adapter.GridSpacingItemDecoration;
import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaSet;
import hs.thang.com.love.gallery.provider.CPHelper;
import hs.thang.com.love.util.Measure;
import hs.thang.com.love.util.PermissionUtils;
import hs.thang.com.love.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

import android.widget.AdapterView.OnItemSelectedListener;

public class GalleryActivity extends AbsActivity implements GridImageAdapter.OnItemClickListener {

    private static final String TAG = "GalleryActivity";

    private RecyclerView mRecyclerView;
    private BaseAdapter mAdapter;
    //private ProgressBar progressBar;
    private GridSpacingItemDecoration mSpacingDecoration;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!PermissionUtils.checkIfAlreadyhavePermission(this)) {
                PermissionUtils.requestForSpecificPermission(this);
            }
        }

        int spanCount = columnsCount();
        mSpacingDecoration = new GridSpacingItemDecoration(spanCount, Measure.pxToDp(0, this), true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_grid_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(mSpacingDecoration);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        mRecyclerView.setItemAnimator(new LandingAnimator(new OvershootInterpolator(1f)));
        mAdapter = new GridImageAdapter(GalleryActivity.this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        initActionBar();
        initSpinner();
        //display(MediaSet.ALL_MEDIA_ALBUM_ID);
    }

    private void initActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private int columnsCount() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                ? 3
                : 4;
    }

    private void initSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<MediaSet> mediaSets = new ArrayList<>();

        CPHelper.getMediasets(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext -> {
                    mediaSets.add(onNext); // onNext = mediaSet
                }, onError -> {
                    Log.e(TAG, "initSpinner: ", onError); // onError = Throwable
                }, () -> {
                    spinner.setAdapter(new AlbumSpinnerAdapter(this, mediaSets));
                });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                MediaSet mediaSet = (MediaSet) adapterView.getItemAtPosition(position);
                display(mediaSet.getmBucketId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });
    }

    private void display(long bucketId) {
        ((GridImageAdapter) mAdapter).clear();
        ArrayList<MediaItem> mediaItems = new ArrayList<>();
        CPHelper.getMedia(this, bucketId, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mediaItem -> {
                            mediaItems.add(mediaItem);
                            Log.d("fuck", "accept");
                        }, throwable -> {
                            Log.wtf("fuck", throwable);
                        },
                        () -> {
                            Log.d("fuck", "run");
                            ((GridImageAdapter) mAdapter).setMediaItems(mediaItems);
                        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onItemClick(MediaItem mediaSet) {
        Uri uri = mediaSet.getUri();
        Intent intent = new Intent();
        intent.setData(uri);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemLongClick(MediaItem mediaSet) {

    }
}
