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
 * Issues List Fragment
 * <p/>
 * <p/>
 * Created by Ramesh on 3/27/2016.
 */
public class IssueListFragment extends Fragment {

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
        String url = "https://api.github.com/repos/rails/rails/issues?sort=updated";

        JsonArrayRequest jreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                String id = jo.getString("id");
                                String title = jo.getString("title");
                                String body = jo.getString("body");
                                String comments_url = jo.getString("comments_url");

                                mIssuesList.add(new IssueInfo(id,
                                        title,
                                        body, comments_url));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        //refresh UI
                        mIssueListAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(IssueListFragment.class.getSimpleName(), "onErrorResponse " + error);
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
        jreq.setTag("issue_list");

        //post the request in volley queue
        GitHubClient.getInstance().addToRequestQueue(jreq);
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
        GitHubClient.getInstance().cancelAllRequest("issue_list");
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(IssueInfo item);
    }
}
