package com.c_schone.apps.photogalleryneu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 04.04.17.
 */

public class PostListFragment extends Fragment {

    private static final String TAG = "PostListFragment";

    private RecyclerView mPostRecyclerView;
    private PostAdapter mAdapter;
    private List<Post> mPostList = new ArrayList<>();
    private FloatingActionButton mFloatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        mPostRecyclerView = (RecyclerView) view
                .findViewById(R.id.fragment_post_recycler_view);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_action_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new FetchItemsTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_post_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.fragment_post_list_menu_switch_to_gallery:
                Intent intent = new Intent(getActivity(), PhotoGalleryActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new PostAdapter(mPostList);
            mPostRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.mPosts = mPostList;
            mAdapter.notifyDataSetChanged();
            Log.d(TAG, "Adapter got notified!");
        }
    }

    private void createPost() {
        Intent intent = new Intent(getActivity(), CreatePostActivity.class);
        startActivity(intent);
    }

    private class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Post mPost;
        private TextView mUserIdTextView;
        private TextView mPostIdTextView;

        public PostHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_post_list_item, parent, false));
            itemView.setOnClickListener(this);

            mUserIdTextView = (TextView) itemView.findViewById(R.id.fragment_post_list_item_user_id);
            mPostIdTextView = (TextView) itemView.findViewById(R.id.fragment_post_list_item_post_id);
        }

        public void bind(Post post) {
            mPost = post;
            mUserIdTextView.setText("User ID " + mPost.getUserId());
            mPostIdTextView.setText("Post ID " + mPost.getId());
        }

        @Override
        public void onClick(View view) {
            Intent intent = PostActivity.newIntent(getActivity(), mPost.getUserId(), mPost.getId());
            startActivity(intent);
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder> {

        private List<Post> mPosts;

        public PostAdapter(List<Post> posts) {
            mPosts = posts;
        }

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PostHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
            Post post = mPosts.get(position);
            holder.bind(post);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected List<Post> doInBackground(Void... params) {
            JSONCommunicator.get().fetchItems();
            return JSONCommunicator.get().getPosts();
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            mPostList = posts;
            Log.d(TAG, "Received Post Example: User Id: " + mPostList.get(0).getUserId()
                        + " Post Id: " + mPostList.get(0).getId()
                        + " Next User Id: " + mPostList.get(1).getUserId()
                        + " Post Id: " + mPostList.get(1).getId());
            updateUI();
        }
    }
}
