package hs.thang.com.love.gallery.provider;

import android.content.ContentResolver;
import android.database.Cursor;

import hs.thang.com.love.data.CursorHandler;
import io.reactivex.Observable;

/**
 * Created by sev_user on 10/4/2017.
 */

public class QueryUtils {

    public static <T> Observable<T> query(Query q, ContentResolver cr, CursorHandler<T> ch) {
        return Observable.create(subscriber -> {
            Cursor cursor = null;
            try {
                cursor = q.getCursor(cr);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        subscriber.onNext(ch.handle(cursor));
                    }
                }
                subscriber.onComplete();
            } catch (Exception ee) {
                subscriber.onError(ee);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }
}
