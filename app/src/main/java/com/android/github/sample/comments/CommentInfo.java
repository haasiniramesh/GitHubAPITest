package com.android.github.sample.comments;

/**
 * Created by Ramesh on 3/27/2016.
 */
public class CommentInfo {
    public final String login;
    public final String body;

    public CommentInfo(String login, String body) {
        this.login = login;
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
