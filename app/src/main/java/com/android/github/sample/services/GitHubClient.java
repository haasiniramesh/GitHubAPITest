package com.android.github.sample.services;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * GitHubClient single instance to Manage N/W call
 *
 *
 * Created by Ramesh on 3/27/2016.
 */
public class GitHubClient {
    private final String BASE_URL = "https://api.github.com";

    private Retrofit mRetrofit;

    private GitHubClient() {
        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    private static class GitHubClientHelper {
        private static GitHubClient INSTANCE = new GitHubClient();
    }

    public static GitHubClient getInstance() {
        return GitHubClientHelper.INSTANCE;
    }

    public Retrofit getRetroFitInstance(){
        return GitHubClientHelper.INSTANCE.mRetrofit;
    }
}
