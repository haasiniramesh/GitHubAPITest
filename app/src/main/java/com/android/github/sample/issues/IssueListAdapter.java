package com.android.github.sample.issues;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.github.sample.R;
import com.android.github.sample.issues.IssueListFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * Issues List Adapter
 * <p/>
 * <p/>
 * Created by Ramesh on 3/27/2016.
 */
public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.ViewHolder> {

    private final List<IssueInfo> mValues;//issues list
    private final OnListFragmentInteractionListener mListener;//listener of list item click

    public IssueListAdapter(List<IssueInfo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText("title: " + mValues.get(position).title);
        holder.mContentView.setText("Desc: " + mValues.get(position).getBody());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mContentView;
        public IssueInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
