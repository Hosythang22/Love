package hs.thang.com.love.gallery.data.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * Created by sev_user on 10/4/2017.
 */

public class ImageFileFilter implements FilenameFilter {

    private Pattern pattern;

    public ImageFileFilter(boolean includeVideo) {
        pattern = includeVideo
                ? Pattern.compile(".(jpg|png|gif|jpe|jpeg|bmp|webp|mp4|mkv|webm|avi)$", Pattern.CASE_INSENSITIVE)
                : Pattern.compile(".(jpg|png|gif|jpe|jpeg|bmp|webp)$", Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean accept(File dir, String filename) {
        return new File(dir, filename).isFile() && pattern.matcher(filename).find();
    }
}
