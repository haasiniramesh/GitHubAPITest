package com.android.github.sample.issues;

/**
 * Created by Ramesh on 3/27/2016.
 */
public class IssueInfo {
    public final String id;
    public final String title;
    public final String body;
    public final String comments_url;

    public IssueInfo(String id, String title, String body, String comments_url) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.comments_url = comments_url;
    }

    public String getBody() {
        if (body.length() > 140)
            return body.substring(0, 139);
        return body;
    }
}
