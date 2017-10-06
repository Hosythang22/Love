package hs.thang.com.love.util;

import android.webkit.MimeTypeMap;

/**
 * Created by sev_user on 10/4/2017.
 */

public class StringUtils {

    public static String getMimeType(String path) {
        int index;
        if (path == null || (index = path.lastIndexOf('.')) == -1)
            return "unknown";

        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(path.substring(index + 1).toLowerCase());
        return  mime != null ? mime : "unknown";
    }
}
