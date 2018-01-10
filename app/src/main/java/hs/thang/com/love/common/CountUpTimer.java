package hs.thang.com.love.common;

import android.content.Context;
import android.os.CountDownTimer;

import hs.thang.com.love.R;

public class CountUpTimer extends CountDownTimer {

    /**
     * @param millisInFuture    The number of millis in the future from the call
     * to {@link #start()} until the countdown is done and {@link #onFinish()}
     * is called.
     * @param countDownInterval The interval along the way to receive
     * {@link #(long)} callbacks.
     */
    private long mCountupStart;
    private Context mContext;

    public CountUpTimer(Context context, long millisInFuture, long countDownInterval) {
        this(millisInFuture, countDownInterval);
        mContext = context;
        mCountupStart = millisInFuture;
    }

    public CountUpTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void onTickdays(String days) {

    }

    public void onTickTimes(String times) {

    }

    @Override
    public void onTick(long millisUntilFinished) {
        String days = Long.toString(mCountupStart / 86400) + " " +mContext.getResources().getString(R.string.days);
        String serverUptimeText =
                String.format("%d H : %d M: %d S",
                        (mCountupStart % 86400) / 3600,
                        ((mCountupStart % 86400) % 3600) / 60,
                        ((mCountupStart % 86400) % 3600) % 60
                );
        mCountupStart += 1;

        onTickdays(days);
        onTickTimes(serverUptimeText);
    }

    @Override
    public void onFinish() {
    }
}
