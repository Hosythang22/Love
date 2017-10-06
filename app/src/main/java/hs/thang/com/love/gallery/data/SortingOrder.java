package hs.thang.com.love.gallery.data;

/**
 * Created by sev_user on 10/4/2017.
 */

public enum SortingOrder {
    ASCENDING (1), DESCENDING (0);

    int value;

    SortingOrder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isAscending(){
        return value == ASCENDING.getValue();
    }

    public static SortingOrder fromValue(boolean value) {
        return value ? ASCENDING : DESCENDING;
    }

    public static SortingOrder fromValue(int value) {
        return value == 0 ? DESCENDING : ASCENDING;
    }
}
