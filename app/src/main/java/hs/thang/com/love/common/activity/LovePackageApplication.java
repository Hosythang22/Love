package hs.thang.com.love.common.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by sev_user on 1/4/2018.
 */

public class LovePackageApplication extends Application {

    private final String TAG = "LovePackageApplication";

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationContext = getApplicationContext(); // save application context as static context
    }

    public static Context getAppContext() {

        /**
         * Application Context should be used carefully.
         */
        return mApplicationContext;
    }
}
