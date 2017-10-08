package hs.thang.com.love.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by DELL on 10/8/2017.
 */

public class EventInfor implements Parcelable, Serializable {

    private String mTextInformation;
    private String mDate;
    private Uri mUri;

    public EventInfor(String mTextInformation, String mDate, Uri mUri) {
        this.mTextInformation = mTextInformation;
        this.mDate = mDate;
        this.mUri = mUri;
    }

    public String getmTextInformation() {
        return mTextInformation;
    }

    public void setmTextInformation(String mTextInformation) {
        this.mTextInformation = mTextInformation;
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
        dest.writeString(this.mTextInformation);
        dest.writeString(this.mDate);
        dest.writeParcelable(this.mUri, flags);
    }

    protected EventInfor(Parcel in) {
        this.mTextInformation = in.readString();
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
}
