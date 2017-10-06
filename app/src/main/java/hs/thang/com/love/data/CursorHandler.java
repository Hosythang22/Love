package hs.thang.com.love.data;

import android.database.Cursor;

import java.sql.SQLException;

/**
 * Created by sev_user on 10/4/2017.
 */

public interface CursorHandler<T> {
    T handle(Cursor cu) throws SQLException;
}
