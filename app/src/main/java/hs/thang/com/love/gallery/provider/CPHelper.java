package hs.thang.com.love.gallery.provider;

import android.content.Context;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import io.reactivex.Observable;

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
    public static Observable<MediaItem> getMedia(Context context, long bucketId, SortingMode sortingMode, SortingOrder sortingOrder) {

        if (bucketId == MediaSet.ALL_MEDIA_ALBUM_ID) {
            return getAllMediaFromMediaStore(context, sortingMode, sortingOrder);
        } else {
            return getMediaFromMediaStore(context, bucketId);
        }
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

    private static Observable<MediaItem> getMediaFromMediaStore(Context context, long bucketId) {
        Query.Builder query = new Query.Builder()
                .uri(MediaStore.Files.getContentUri("external"))
                .projection(MediaItem.getProjection());

        query.selection(String.format("%s=? and %s=?",
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Images.Media.BUCKET_ID));
        query.args(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE, bucketId);

        return QueryUtils.query(query.build(), context.getContentResolver(), MediaItem::new);
    }
}
