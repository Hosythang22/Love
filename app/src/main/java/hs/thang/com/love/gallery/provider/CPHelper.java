package hs.thang.com.love.gallery.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import hs.thang.com.love.gallery.data.filter.ImageFileFilter;
import io.reactivex.Observable;

import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.util.ArrayList;

import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaSet;
import hs.thang.com.love.gallery.data.SortingMode;
import hs.thang.com.love.gallery.data.SortingOrder;

public class CPHelper {

    //region MediaSet
    public static Observable<MediaSet> getMedias(Context context) {
        Query.Builder builder = new Query.Builder()
                .uri(MediaStore.Files.getContentUri("external"))
                .projection(MediaSet.getProjection())
                .selection(String.format("%s=?) group by (%s) %s ",
                        MediaStore.Files.FileColumns.MEDIA_TYPE,
                        MediaStore.Files.FileColumns.PARENT));
        return QueryUtils.query(builder.build(), context.getContentResolver(), MediaSet::new);
    }

    //region Media
    public static Observable<MediaItem> getMedia(Context context, MediaSet mediaSet, SortingMode sortingMode, SortingOrder sortingOrder) {

        if (mediaSet.getId() == -1) {
            return getMediaFromStorage(context, mediaSet);
        } else if (mediaSet.getId() == MediaSet.ALL_MEDIA_ALBUM_ID) {
            return getAllMediaFromMediaStore(context, sortingMode, sortingOrder);
        } else {
            return getMediaFromMediaStore(context, mediaSet, sortingMode, sortingOrder);
        }
    }

    private static Observable<MediaItem> getMediaFromStorage(Context context, MediaSet album) {

        return Observable.create(subscriber -> {
            File dir = new File(album.getName());
            File[] files = dir.listFiles(new ImageFileFilter(Hawk.get("set_include_video", true)));
            try {
                if (files != null && files.length > 0)
                    for (File file : files)
                        subscriber.onNext(new MediaItem(file));
                subscriber.onComplete();

            } catch (Exception err) {
                subscriber.onError(err);
            }
        });

    }

    private static Observable<MediaItem> getAllMediaFromMediaStore(Context context, SortingMode sortingMode, SortingOrder sortingOrder) {
        Query.Builder query = new Query.Builder()
                .uri(MediaStore.Files.getContentUri("external"))
                .projection(MediaItem.getProjection());

        query.selection(String.format("%s=?",
                MediaStore.Files.FileColumns.MEDIA_TYPE));
        query.args(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);

        return QueryUtils.query(query.build(), context.getContentResolver(), MediaItem :: new);
    }

    private static Observable<MediaItem> getMediaFromMediaStore(Context context, MediaSet album, SortingMode sortingMode, SortingOrder sortingOrder) {

        Query.Builder query = new Query.Builder()
                .uri(MediaStore.Files.getContentUri("external"))
                .projection(MediaItem.getProjection())
                .sort(sortingMode.getMediaColumn())
                .ascending(sortingOrder.isAscending());

        if (Hawk.get("set_include_video", true)) {
            query.selection(String.format("(%s=? or %s=?) and %s=?",
                    MediaStore.Files.FileColumns.MEDIA_TYPE,
                    MediaStore.Files.FileColumns.MEDIA_TYPE,
                    MediaStore.Files.FileColumns.PARENT));
            query.args(
                    MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
                    MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO,
                    album.getId());
        } else {
            query.selection(String.format("%s=? and %s=?",
                    MediaStore.Files.FileColumns.MEDIA_TYPE,
                    MediaStore.Files.FileColumns.PARENT));
            query.args(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE, album.getId());
        }

        return QueryUtils.query(query.build(), context.getContentResolver(), MediaItem::new);
    }

    public static ArrayList<MediaItem> getAllMediaItem(Context context) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        ArrayList<MediaItem> mediaItems = new ArrayList<>();
        String photoFilePath;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                photoFilePath = cursor.getString(
                        cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                mediaItems.add(new MediaItem(photoFilePath));
            }
            cursor.close();
        }

        return mediaItems;
    }

    public static ArrayList<MediaSet> getAllAlbum(Context context) {

        ArrayList<MediaSet> mediaSets = new ArrayList<>();

        String[] PROJECTION_BUCKET = {MediaStore.Images.ImageColumns.BUCKET_ID,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.DATA};

        String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";

        String BUCKET_ORDER_BY = "MAX(datetaken) DESC";

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = context.getContentResolver().query(images, PROJECTION_BUCKET,
                BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

        String bucket;
        String date;
        String coverPath;
        long bucketId;

        if (cur.moveToFirst()) {
            do {
                bucket = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                date = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
                coverPath = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATA));
                bucketId = cur.getInt(cur.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));

                if (bucket != null && bucket.length() > 0) {
                    mediaSets.add(new MediaSet(bucket, bucketId, coverPath));
                    Log.v("ListingImages", " bucket=" + bucket
                            + "  date_taken=" + date + "  _data=" + coverPath
                            + " bucket_id=" + bucketId);
                }
            } while (cur.moveToNext());
        }
        cur.close();

        return mediaSets;
    }
}
