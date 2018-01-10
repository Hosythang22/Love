package hs.thang.com.love.common.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import hs.thang.com.love.common.Model;
import hs.thang.com.love.gallery.provider.Query;
import hs.thang.com.love.gallery.provider.QueryUtils;
import io.reactivex.Observable;

import hs.thang.com.love.data.EventInformation;

public class ProviderUtil {

    public static Observable<EventInformation> getEventInfor(Context context) {
        Uri uri = EventsCaseContract.Events.CONTENT_URI;
        Query.Builder builder = new Query.Builder()
                .uri(uri);

        return QueryUtils.query(builder.build(), context.getContentResolver(), EventInformation::new);
    }

    public static void insertEvents(Context context, EventInformation eventInfor) {
        ContentValues values = new ContentValues();

        values.put(EventsCaseContract.Events.KEY_CONTENT, eventInfor.getmContent());
        values.put(EventsCaseContract.Events.KEY_DATE, eventInfor.getmDate());
        values.put(EventsCaseContract.Events.KEY_URI_COVER, String.valueOf(eventInfor.getmUri()));
        values.put(EventsCaseContract.Events.KEY_HOWLONG, eventInfor.getmHowlong());

        context.getContentResolver().insert(EventsCaseContract.Events.CONTENT_URI, values);
    }

    public static ArrayList<EventInformation> getEvents(Context context) {

        Uri uri = EventsCaseContract.Events.CONTENT_URI;

        ArrayList<EventInformation> events = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {
                    EventInformation sh = cursorToEvent(cursor);
                    events.add(sh);
                } while (cursor.moveToNext());

            }
        }

        return events;
    }
    private static EventInformation cursorToEvent(Cursor cursor) {

        EventInformation sh = new EventInformation();

        sh.setmContent(cursor.getString(1));
        sh.setmDate(cursor.getString(2));
        sh.setmHowlong(cursor.getString(3));
        sh.setmUri(Uri.parse(cursor.getString(4)));


        return sh;
    }

    public static void deleteEvent(Context context, long id) {
        context.getContentResolver().delete(
                EventsCaseContract.Events.CONTENT_URI,
                EventsCaseContract.Events.KEY_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

    }
}
