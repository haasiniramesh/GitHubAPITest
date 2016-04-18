package com.android.github.sample.issues;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.github.sample.R;
import com.android.github.sample.SimpleDividerItemDecoration;
import com.android.github.sample.model.Issue;
import com.android.github.sample.services.GitHubClient;
import com.android.github.sample.services.IssueService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Issues List Fragment
 * <p/>
 * <p/>
 * Created by Ramesh on 3/27/2016.
 */
public class IssueListFragment extends Fragment {
    private final String TAG = IssueListFragment.class.getSimpleName();

    private OnListFragmentInteractionListener mListener;//listener for fragments event handler
    private IssueListAdapter mIssueListAdapter;//List Adapter
    public List<IssueInfo> mIssuesList;//issues list

    public IssueListFragment() {
        mIssuesList = new ArrayList<IssueInfo>();
    }

    public static IssueListFragment newInstance() {
        IssueListFragment fragment = new IssueListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestIssueList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            mIssueListAdapter = new IssueListAdapter(mIssuesList, mListener);
            recyclerView.setAdapter(mIssueListAdapter);

            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        }
        return view;
    }

    //Request GitHub Issues list
    private void requestIssueList() {

        IssueService issueService = GitHubClient.getInstance().getRetroFitInstance().create(IssueService.class);
        Observable<List<Issue>> issueList = issueService.getIssues("rails", "rails");

        issueList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Issue>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError "+ e.getMessage());
                    }

                    @Override
                    public void onNext(List<Issue> response) {
                        Log.d(TAG, "onNext "+response.size());

                        refreshUI(response);
                    }
                });
    }

    private void refreshUI(List<Issue> issueList){
        for (Issue issue: issueList) {
            String id = issue.getId().toString();
            String title = issue.getTitle();
            String body = issue.getBody();
            String commentsURL = issue.getCommentsUrl();

            mIssuesList.add(new IssueInfo(id,
                    title,
                    body, commentsURL, issue.getNumber().toString()));
        }

        //refresh UI
        mIssueListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //cancel the pending requests
        //TODO explore retrofit canceling the pending request
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(IssueInfo item);
    }
}
