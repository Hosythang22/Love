package hs.thang.com.love.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by sev_user on 10/10/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database version,  database name
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "events.db";

    public DBHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createEventsTable(db);

    }

    private void createEventsTable(SQLiteDatabase database) {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE ");

        builder.append(EventsCaseContract.Events.TABLE_NAME);

        builder.append('(');

        builder.append(EventsCaseContract.Events.KEY_ID);

        builder.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        builder.append(EventsCaseContract.Events.KEY_CONTENT);

        builder.append(" TEXT NOT NULL, ");

        builder.append(EventsCaseContract.Events.KEY_DATE);

        builder.append(" TEXT NOT NULL, ");

        builder.append(EventsCaseContract.Events.KEY_HOWLONG);

        builder.append(" TEXT NOT NULL, ");

        builder.append(EventsCaseContract.Events.KEY_URI_COVER);

        builder.append(" TEXT NOT NULL");

        /*builder.append(EventsCaseContract.Events.KEY_STATE);

        builder.append(" INTEGER");*/

        builder.append(");");

        database.execSQL(builder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + EventsCaseContract.Events.TABLE_NAME);

        // Creating tables again
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onOpen(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onOpen(db);
    }
}
