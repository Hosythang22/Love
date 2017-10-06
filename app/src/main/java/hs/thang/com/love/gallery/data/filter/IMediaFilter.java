package hs.thang.com.love.gallery.data.filter;

import hs.thang.com.love.gallery.data.MediaItem;

/**
 * Created by sev_user on 10/4/2017.
 */

public interface IMediaFilter {
    boolean accept(MediaItem mediaItem);
}
