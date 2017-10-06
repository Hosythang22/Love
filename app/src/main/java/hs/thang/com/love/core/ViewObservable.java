package hs.thang.com.love.core;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by sev_user on 3/13/2017.
 */

public class ViewObservable {

    private final LocalObservable mObservable = new LocalObservable();

    public void notifyObservers(Object data) {
        mObservable.setChanged();
        mObservable.notifyObservers(data);
    }

    public void addObserver(Observer obs) {
        mObservable.addObserver(obs);
    }

    private static class LocalObservable extends Observable {

        @Override
        public void setChanged() {
            // TODO: need to force this to public so notifyObservers works???
            // why is it protected by default?
            super.setChanged();
        }
    }
}
