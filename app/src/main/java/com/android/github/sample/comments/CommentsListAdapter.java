package com.android.github.sample.comments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.github.sample.R;

import java.util.List;

/**
 * Comments List Adapter
 * <p/>
 * <p/>
 * Created by Ramesh on 3/27/2016.
 */
public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {
    private final List<CommentInfo> mCommentsList;//comments list

    public CommentsListAdapter(List<CommentInfo> items) {
        mCommentsList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mCommentsList.get(position);
        holder.mUserNameView.setText("user: " + mCommentsList.get(position).login);
        holder.mContentView.setText("comment: " + mCommentsList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUserNameView;
        public final TextView mContentView;
        public CommentInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserNameView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
