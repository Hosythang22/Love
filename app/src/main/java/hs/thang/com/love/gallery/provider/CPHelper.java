package hs.thang.com.love.gallery.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.provider.MediaStore.Images;

import hs.thang.com.love.gallery.data.filter.ImageFileFilter;
import io.reactivex.Observable;

import com.orhanobut.hawk.Hawk;

import java.io.File;

import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaSet;
import hs.thang.com.love.gallery.data.SortingMode;
import hs.thang.com.love.gallery.data.SortingOrder;

public class CPHelper {

    //region MediaSet
    public static Observable<MediaSet> getMediasets(Context context) {

        final String where = Images.ImageColumns.BUCKET_ID + "!=0) GROUP BY (" + Images.ImageColumns.BUCKET_ID + " ";
        String BUCKET_ORDER_BY = "MAX(date_modified)";

        Query.Builder builder = new Query.Builder()
                .uri(Images.Media.EXTERNAL_CONTENT_URI)
                .projection(MediaSet.getProjection())
                .selection(where)
                .sort(BUCKET_ORDER_BY);
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

        return QueryUtils.query(query.build(), context.getContentResolver(), MediaItem::new);
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
}
