package com.android.github.sample;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ramesh on 3/27/2016.
 */
public class GitHubApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
