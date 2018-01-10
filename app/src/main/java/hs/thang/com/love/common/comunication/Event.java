package hs.thang.com.love.common.comunication;

/**
 * Created by sev_user on 3/14/2017.
 */

public class Event {

    private static int index = 1;

    public static final int EVENT_RENAME_MEDIA = index++;
    public static final int EVENT_UPDATE_BACKGROUND_FOR_FRAGMENT = index++;

    private int mType;

    private Object mData;

    private int mIntVal;

    public static class Builder {

        public static Event create() {
            return new Event();
        }
    }

    public Event setType(int eventType) {
        this.mType = eventType;
        return this;
    }

    public Event setData(Object eventData) {
        this.mData = eventData;
        return this;
    }

    public Event setIntData(int intVal) {
        this.mIntVal = intVal;
        return this;
    }

    public int getType() {
        return mType;
    }

    public Object getData() {
        return mData;
    }

    public int getIntData() {
        return mIntVal;
    }
}
