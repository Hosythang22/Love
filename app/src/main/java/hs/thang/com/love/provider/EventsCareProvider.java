package hs.thang.com.love.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sev_user on 10/10/2017.
 */

public class EventsCareProvider extends ContentProvider {

    private static final String TAG = EventsCareProvider.class.getSimpleName();

    private DBHandler mDbHandler;

    static final int EVENTS_URI_TYPE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(EventsCaseContract.AUTHORITY, EventsCaseContract.Events.BASE_PATH, EVENTS_URI_TYPE);

    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, " onCreate ");
        mDbHandler = new DBHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        String groupBy = null;
        int match = sUriMatcher.match(uri);
        Log.d(TAG, " query uri " + uri + " match " + match);
        SQLiteDatabase db = mDbHandler.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        String tableName = getTableNameFromMatch(match);

        builder.setTables(tableName);

        cursor = builder
                .query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    private String getTableNameFromMatch(int match) {
        switch (match) {

            case EVENTS_URI_TYPE:
                return EventsCaseContract.Events.TABLE_NAME;
        }
        return null;
    }

    private Uri buildUriFromMatch(int match, long id) {
        switch (match) {

            case EVENTS_URI_TYPE:
                return EventsCaseContract.Events.buildUri(id);
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, " getType Uri =" + uri);
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHandler.getWritableDatabase();

        Uri mUri;

        int match = sUriMatcher.match(uri);


        String tableName = getTableNameFromMatch(match);


        long _id = db.insert(tableName, null, values);

        Log.d(TAG, " insert uri " + uri + ", match = " + match + ", _id= " + _id);

        if (_id > 0) {
            mUri = buildUriFromMatch(match, _id);
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }

        if (mUri != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return mUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHandler.getWritableDatabase();

        // this makes delete all rows return the number of rows deleted

        if (null == selection) selection = "1";

        int match = sUriMatcher.match(uri);

        String tableName = getTableNameFromMatch(match);

        int rowsDeleted;

        rowsDeleted = db.delete(tableName, selection, selectionArgs);

        Log.d(TAG, " delete uri " + uri + ", match = " + match + ", rowsDeleted= " + rowsDeleted);

        // Because a null deletes all rows

        if (rowsDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);

        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHandler.getWritableDatabase();

        int rowsUpdated;

        int match = sUriMatcher.match(uri);

        Log.d(TAG, " update uri " + uri + " match " + match);

        String tableName = getTableNameFromMatch(match);

        rowsUpdated = db.update(tableName, values, selection, selectionArgs);

        Log.d(TAG, " update uri " + uri + ", match = " + match + ", rowsUpdated= " + rowsUpdated);

        if (rowsUpdated != 0) {

            getContext().getContentResolver().notifyChange(uri, null);

        }

        return rowsUpdated;
    }
}
