package com.android.github.sample.services;

import com.android.github.sample.model.Comments;
import com.android.github.sample.model.Issue;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by anandhar on 4/18/16.
 */
public interface IssueService {

    //sample
    //https://api.github.com/repos/rails/rails/issues?sort=updated
    @GET("/repos/{org}/{orgs}/issues?sort=updated")
    Observable<List<Issue>> getIssues(@Path("org") String org, @Path("orgs") String orgs);

    @GET("/repos/rails/rails/issues?sort=updated")
    Observable<List<Issue>> getIssues();

    //sample
    //"https://api.github.com/repos/rails/rails/issues/24603/comments",
    @GET("/repos/{org}/{orgs}/issues/{issue}/comments")
    Observable<List<Comments>> getIssueComments(@Path("org") String org,
                                                @Path("orgs") String orgs,
                                                @Path("issue") String issue);
}
