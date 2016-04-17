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
import android.widget.Toast;

import com.android.github.sample.R;
import com.android.github.sample.SimpleDividerItemDecoration;
import com.android.github.sample.services.GitHubClient;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Comments List Fragment
 * <p/>
 * <p/>
 * Created by Ramesh on 3/27/2016.
 */
public class CommentsListFragment extends Fragment {

    private static final String ARG_COMMENT_URL = "comment_url";
    private String mCommentUrl;//Github comments url
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
            mCommentUrl = getArguments().getString(ARG_COMMENT_URL);
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
        String url = mCommentUrl;

        JsonArrayRequest jreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0)
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jo = response.getJSONObject(i);
                                    JSONObject user = jo.getJSONObject("user");

                                    String login = user.getString("login");
                                    String body = jo.getString("body");

                                    mCommentsList.add(new CommentInfo(login,
                                            body));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        else {
                            String login = "no comments";
                            String body = "";
                            mCommentsList.add(new CommentInfo(login,
                                    body));
                        }

                        mIssueListRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(CommentsListFragment.class.getSimpleName(), "onErrorResponse " + error);
                String msg = null;
                if (error instanceof NoConnectionError) {
                    msg = "No Connection";
                } else if (error instanceof ServerError) {
                    msg = "Server Error";
                } else if (error instanceof NetworkError) {
                    msg = "Network Error";
                } else {
                    msg = "Unknown Error";
                }

                Toast.makeText(getActivity(),
                        msg,
                        Toast.LENGTH_LONG).show();
            }
        });

        //set tag to cancel the issue later
        jreq.setTag("comments_list");

        //post the request in volley queue
        GitHubClient.getInstance().addToRequestQueue(jreq);
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

        GitHubClient.getInstance().cancelAllRequest("comments_list");
    }
}
