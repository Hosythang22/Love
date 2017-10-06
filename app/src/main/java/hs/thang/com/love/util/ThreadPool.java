package hs.thang.com.love.util;

public class ThreadPool {

    public interface Job<T> {

        T run(JobContext jc);
    }

    public interface JobContext {

        boolean isCancelled();

        void setCancelListener(CancelListener listener);

        boolean setMode(int mode);

        int getMode();
    }

    public interface CancelListener {

        void onCancel();
    }
}
