package hs.thang.com.love.gallery.data;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by sev_user on 9/29/2017.
 */

@SuppressLint("ParcelCreator")
public class MediaSet extends MediaObject {

    private long mId = -1;
    public static final long ALL_MEDIA_ALBUM_ID = 8000;
    private String mName;
    private String mCoverPath;
    private File mFile;

    public MediaSet(Cursor cur) {
        Log.wtf("fuck1", "run = "
                + cur.getLong(0) + ", "
                + cur.getString(1) + ", "
                + cur.getInt(2) + ", "
                + cur.getString(3) + ", "
                + cur.getLong(4) + ", ");
    }

    public MediaSet() {
    }

    public MediaSet(File file) {
        mFile = file;
    }

    public MediaSet(String name, long id) {
        mName = name;
        mId = id;
    }

    public MediaSet(String name, long id, String coverPath) {
        mName = name;
        mId = id;
        mCoverPath = coverPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public Object handle(Cursor cu) throws SQLException {
        return null;
    }

    public long getId() {
        return mId;
    }

    public static String[] getProjection() {
        return new String[]{
                MediaStore.Files.FileColumns.PARENT,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                "count(*)",
                MediaStore.Images.Media.DATA,
                "max(" + MediaStore.Images.Media.DATE_MODIFIED + ")"
        };
    }

    public String getName() {
        return mName;
    }

    private String getCoverPath() {
        return mCoverPath;
    }
}
