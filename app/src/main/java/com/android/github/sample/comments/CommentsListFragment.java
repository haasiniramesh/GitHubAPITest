package com.android.github.sample.comments;

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
import com.android.github.sample.model.Comments;
import com.android.github.sample.services.GitHubClient;
import com.android.github.sample.services.IssueService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Comments List Fragment
 * <p/>
 * <p/>
 * Created by Ramesh on 3/27/2016.
 */
public class CommentsListFragment extends Fragment {
    private static final String TAG = CommentsListFragment.class.getSimpleName();
    private static final String ARG_COMMENT_URL = "comment_url";
    private String mNumber;//Github comments url
    public List<CommentInfo> mCommentsList;//comments list

    private CommentsListAdapter mIssueListRecyclerViewAdapter;//comments list adapter

    public CommentsListFragment() {
        mCommentsList = new ArrayList<CommentInfo>();
    }

    public static CommentsListFragment newInstance(String comment_url) {
        //add comments url in bundle
        CommentsListFragment fragment = new CommentsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMMENT_URL, comment_url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //retrieve the comments url from bundle argument
        if (getArguments() != null) {
            mNumber = getArguments().getString(ARG_COMMENT_URL);
        }

        //get comments list
        requestCommentsList();
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

            mIssueListRecyclerViewAdapter = new CommentsListAdapter(mCommentsList);
            recyclerView.setAdapter(mIssueListRecyclerViewAdapter);

            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    //Request GitHub issue comments list
    private void requestCommentsList() {
        String number = mNumber;

        IssueService issueService = GitHubClient.getInstance().getRetroFitInstance().create(IssueService.class);
        Observable<List<Comments>> commentsList = issueService.getIssueComments("rails", "rails", number);

        commentsList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comments>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(List<Comments> comments) {
                        Log.d(TAG, "onNext "+comments.size());
                        refreshUi(comments);
                    }
                });
    }


    private void refreshUi(List<Comments> comments) {
        if (comments.size() > 0)
            for (Comments comment : comments) {

                mCommentsList.add(new CommentInfo(comment.getUser().getLogin(),
                        comment.getBody()));

            }
        else {
            String login = "no comments";
            String body = "";
            mCommentsList.add(new CommentInfo(login,
                    body));
        }

        mIssueListRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //GitHubClient.getInstance().cancelAllRequest("comments_list");
        //TODO explore retrofit request cancel API
    }
}
