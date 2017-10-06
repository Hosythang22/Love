package hs.thang.com.love.gallery;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

import hs.thang.com.love.AbsActivity;
import hs.thang.com.love.gallery.adapter.AlbumAdapter;
import hs.thang.com.love.gallery.adapter.DividerItemDecoration;
import hs.thang.com.love.gallery.adapter.GalleryBaseAdapter;
import hs.thang.com.love.gallery.adapter.GridImageAdapter;
import hs.thang.com.love.gallery.adapter.GridSpacingItemDecoration;
import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaObject;
import hs.thang.com.love.gallery.data.MediaSet;
import hs.thang.com.love.gallery.data.SortingMode;
import hs.thang.com.love.gallery.data.filter.MediaFilter;
import hs.thang.com.love.gallery.provider.CPHelper;
import hs.thang.com.love.util.Measure;
import hs.thang.com.love.util.PermissionUtils;
import hs.thang.com.thu.R;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

import android.widget.AdapterView.OnItemSelectedListener;

import com.orhanobut.hawk.Hawk;

public class GalleryActivity extends AbsActivity implements OnItemSelectedListener {

    private static final String TAG = "GalleryActivity";
    private static final int NUMBER_OF_COLUMNS = 3;
    private ArrayList<MediaItem> mMediaItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private GalleryBaseAdapter mAdapter;
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
        mSpacingDecoration = new GridSpacingItemDecoration(spanCount, Measure.pxToDp(3, this), true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_grid_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(mSpacingDecoration);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        mRecyclerView.setItemAnimator(new LandingAnimator(new OvershootInterpolator(1f)));

        initActionBar();
        initSpinner();
        display();
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
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");
        categories.add("Travel");
        categories.add("Travel");
        categories.add("Travel");
        categories.add("Travel");
        categories.add("Travel");
        categories.add("Travel");
        categories.add("Travel");
        categories.add("Travel");
        categories.add("Travel");


        /*ArrayList<MediaSet> mediaSets = CPHelper.getAllAlbum(this);*/

        ArrayList<MediaSet> mediaSets = new ArrayList<>();

        CPHelper.getMedias(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MediaSet>() {
                    @Override
                    public void accept(MediaSet mediaSet) throws Exception {
                        mediaSets.add(mediaSet);
                        Log.wtf("fuck1", "accept");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.wtf("fuck1", "throwable");

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.wtf("fuck1", "run");

                    }
                });

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        /*AlbumAdapter albumAdapter = new AlbumAdapter(this, mediaSets);*/

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void display() {
        CPHelper.getMedia(this, new MediaSet(null, MediaSet.ALL_MEDIA_ALBUM_ID), null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mediaItem -> {
                            mMediaItems.add(mediaItem);
                            Log.wtf("fuck", "accept");
                        }, throwable -> {
                            Log.wtf("fuck", throwable);
                        },
                        () -> {
                            Log.wtf("fuck", "run");
                            mAdapter = new GridImageAdapter(GalleryActivity.this, mMediaItems);
                            mRecyclerView.setAdapter(mAdapter);
                        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        /*Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
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
}
