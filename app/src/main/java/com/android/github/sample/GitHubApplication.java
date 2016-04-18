package com.android.github.sample;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Ramesh on 3/27/2016.
 */
public class GitHubApplication extends Application {

    private static Context mContext;

    private static GitHubApplication mGitHubApplication;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mGitHubApplication = this;

        mRefWatcher = LeakCanary.install(this);
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static GitHubApplication getInstance() {
        return mGitHubApplication;
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }
}
