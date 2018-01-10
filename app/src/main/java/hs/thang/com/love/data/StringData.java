package hs.thang.com.love.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sev_user on 3/14/2017.
 */

public class StringData {
    private static final String PREFS_NAME = "hs.thang.com.love.data";
    public static final String KEY_NICKNAME_1 = "key_nickname_1";
    public static final String KEY_NICKNAME_2 = "key_nickname_2";
    public static final String KEY_START_DATE = "key_start_date";
    public static final String KEY_START_DATE_FULL = "key_start_date_full";


    public static boolean setPreference(Context c, String key, String value) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    static public String getPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        String value = settings.getString(key, "");
        return value;
    }
}
