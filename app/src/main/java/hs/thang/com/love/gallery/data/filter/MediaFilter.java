package hs.thang.com.love.gallery.data.filter;

import hs.thang.com.love.gallery.data.MediaItem;
import hs.thang.com.love.gallery.data.MediaSet;

/**
 * Created by sev_user on 10/4/2017.
 */

public class MediaFilter {
    public static IMediaFilter getFilter(FilterMode mode) {
        switch (mode) {
            case ALL: default:
                return media -> true;
            case GIF:
                return MediaItem::isGif;
            case VIDEO:
                return MediaItem::isVideo;
            case IMAGES: return MediaItem::isImage;
        }
    }
}
