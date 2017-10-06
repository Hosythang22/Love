package hs.thang.com.love.util;

public interface Future<T> {

    void cancel();

    boolean isCancelled();

    boolean isDone();

    T get();

    void waitDone();
}
