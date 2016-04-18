package com.android.github.sample;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.github.sample.comments.CommentsListFragment;
import com.android.github.sample.issues.IssueInfo;
import com.android.github.sample.issues.IssueListFragment;

/**
 * Activity for Issues List & Comments List
 *
 * Created by Ramesh on 3/27/2016.
 */
public class GitHubIssueActivity extends AppCompatActivity implements IssueListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_issue);

        FragmentManager mgr = getSupportFragmentManager();
        FragmentTransaction transaction = mgr.beginTransaction();

        IssueListFragment fragment = IssueListFragment.newInstance();
        transaction.replace(R.id.container, fragment, "list");

        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(IssueInfo item) {
        FragmentManager mgr = getSupportFragmentManager();
        FragmentTransaction transaction = mgr.beginTransaction();

        CommentsListFragment fragment = CommentsListFragment.newInstance(item.number);
        transaction.replace(R.id.container, fragment, "comments");
        transaction.addToBackStack("");

        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        GitHubApplication.getInstance().getRefWatcher().watch(this);
    }
}
