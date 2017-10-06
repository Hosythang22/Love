package hs.thang.com.love.gallery.data;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;

import java.io.File;
import java.sql.SQLException;

import hs.thang.com.love.util.StringUtils;

@SuppressLint("ParcelCreator")
public class MediaItem extends MediaObject {

    public static final int MEDIA_TYPE_IMAGE = 0;
    public static final int MEDIA_TYPE_VIDEO = 1;

    public float mRotation;

    private String title;
    private String thumbnail;

    private String mPath = null;
    private long mDateModified = -1;
    private String mMimeType = "unknown";
    private int mOrientation = 0;

    private String mUriString = null;

    private long mSize = -1;
    private boolean mSelected = false;

    public MediaItem() {

    }

    public MediaItem(File file) {
        this(file.getPath());
        mSize = file.length();
        mMimeType = StringUtils.getMimeType(mPath);
    }

    public MediaItem(String path) {
        mPath = path;
        mMimeType = StringUtils.getMimeType(path);
    }

    public boolean isImage() { return mMimeType.startsWith("image"); }

    public boolean isVideo() { return mMimeType.startsWith("video"); }

    public Uri getUri() {
        return mUriString != null ? Uri.parse(mUriString) : Uri.fromFile(new File(mPath));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public boolean isGif() { return mMimeType.endsWith("gif"); }

    public String getPath() {
        return mPath;
    }

    @Override
    public Object handle(Cursor cu) throws SQLException {
        return new MediaItem(cu);
    }

    public static String[] getProjection() {
        return new String[]{
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.ORIENTATION
        };
    }

    public MediaItem(Cursor cur) {
        mPath = cur.getString(0);
        mDateModified = cur.getLong(1);
        mMimeType = cur.getString(2);
        mSize = cur.getLong(3);
        mOrientation = cur.getInt(4);
    }
}
