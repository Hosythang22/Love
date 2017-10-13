package hs.thang.com.love.data;

import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Created by DELL on 10/8/2017.
 */

public class EventInfor implements Parcelable, Serializable, CursorHandler {

    private String mContent;
    private String mDate;
    private Uri mUri;
    private String mHowlong;

    public EventInfor() {
    }

    public EventInfor(Cursor cursor) {
        mContent = cursor.getString(1);
        mDate = cursor.getString(2);
        mHowlong = cursor.getString(3);
        mUri = Uri.parse(cursor.getString(4));
    }

    public EventInfor(String mTextInformation, String date, Uri uri, String howlong) {
        mContent = mTextInformation;
        mDate = date;
        mUri = uri;
        mHowlong = howlong;
    }

    public String getmHowlong() {
        return mHowlong;
    }

    public void setmHowlong(String mHowlong) {
        this.mHowlong = mHowlong;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public Uri getmUri() {
        return mUri;
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mContent);
        dest.writeString(this.mDate);
        dest.writeParcelable(this.mUri, flags);
    }

    protected EventInfor(Parcel in) {
        this.mContent = in.readString();
        this.mDate = in.readString();
        this.mUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<EventInfor> CREATOR = new Creator<EventInfor>() {
        @Override
        public EventInfor createFromParcel(Parcel source) {
            return new EventInfor(source);
        }

        @Override
        public EventInfor[] newArray(int size) {
            return new EventInfor[size];
        }
    };

    @Override
    public Object handle(Cursor cu) throws SQLException {
        return null;
    }
}
