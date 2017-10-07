package hs.thang.com.love.gallery.data;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
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
    private String mAlbumName;
    private int mNumberOfImage;
    private String mUriCover;
    private String mPath;
    private long mBucketId;

    public MediaSet(Cursor cur) {
        mBucketId = cur.getLong(0);
        mAlbumName = cur.getString(1);
        mNumberOfImage = cur.getInt(2);
        mPath = cur.getString(3);
    }

    public long getmBucketId() {
        return mBucketId;
    }

    public void setmBucketId(long mBucketId) {
        this.mBucketId = mBucketId;
    }

    public Uri getUri() {
        return mUriCover != null ? Uri.parse(mUriCover) : Uri.fromFile(new File(mPath));
    }

    public String getmAlbumName() {
        return mAlbumName;
    }

    public void setmAlbumName(String mAlbumName) {
        this.mAlbumName = mAlbumName;
    }

    public int getmNumberOfImage() {
        return mNumberOfImage;
    }

    public void setmNumberOfImage(int mNumberOfImage) {
        this.mNumberOfImage = mNumberOfImage;
    }

    public String getmUriCover() {
        return mUriCover;
    }

    public void setmUriCover(String mUriCover) {
        this.mUriCover = mUriCover;
    }

    public MediaSet(String name, long id) {
        mAlbumName = name;
        mId = id;
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
        return new MediaSet(cu);
    }

    public long getId() {
        return mId;
    }

    public static String[] getProjection() {
        return new String[]{
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                "count(*)",
                MediaStore.Images.Media.DATA,
                "max(" + MediaStore.Images.Media.DATE_MODIFIED + ")"
        };
    }

    public String getName() {
        return mAlbumName;
    }

    private String getCoverPath() {
        return mUriCover;
    }
}
