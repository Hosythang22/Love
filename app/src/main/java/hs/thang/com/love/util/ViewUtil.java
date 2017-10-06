package hs.thang.com.love.util;

public class ViewUtil {

    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh,
                                                  double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }
}
