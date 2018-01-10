package hs.thang.com.love.common.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sev_user on 10/10/2017.
 */

public class EventsCaseContract {

    public static final String AUTHORITY = "hs.thang.com.love";

    public static class Events implements BaseColumns {

        public static final String BASE_PATH = "events";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

        public static final String TABLE_NAME = "Events";

        public static final String KEY_ID = "event_id";

        public static final String KEY_CONTENT = "event_content";

        public static final String KEY_DATE = "event_date";

        public static final String KEY_HOWLONG = "event_how_long";

        public static final String KEY_URI_COVER = "event_uri_cover";

        public static Uri buildUri(long id) {

            return ContentUris.withAppendedId(CONTENT_URI, id);

        }

    }


}
