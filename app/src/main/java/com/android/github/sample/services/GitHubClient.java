package com.android.github.sample.services;

import com.android.github.sample.GitHubApplication;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * GitHubClient single instance to Manage Volley Request Queue
 *
 *
 * Created by Ramesh on 3/27/2016.
 */
public class GitHubClient {
    private RequestQueue mRequestQueue;//volley network request queue

    private GitHubClient() {
        mRequestQueue = getRequestQueue();
    }

    private static class GitHubClientHelper {
        private static GitHubClient INTANCE = new GitHubClient();
    }

    public static GitHubClient getInstance() {
        return GitHubClientHelper.INTANCE;
    }

    //cancel volley request
    public void cancelAllRequest(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    //add new request into volley queue
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(GitHubApplication.getAppContext());
        }
        return mRequestQueue;
    }
}
