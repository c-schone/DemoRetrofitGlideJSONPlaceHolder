package com.c_schone.apps.photogalleryneu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by christian on 04.04.17.
 */

public class PostFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_POST_ID = "post_id";

    private Post mPost;
    private TextView mUserIdText;
    private TextView mPostIdText;
    private TextView mTitleText;
    private TextView mBodyText;

    public static PostFragment newInstance(int user_id, int post_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, user_id);
        args.putInt(ARG_POST_ID, post_id);

        PostFragment postFragment = new PostFragment();
            postFragment.setArguments(args);
            return postFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int userId = getArguments().getInt(ARG_USER_ID);
        int postId = getArguments().getInt(ARG_POST_ID);
        mPost = JSONCommunicator.get().getPost(userId, postId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, container, false);

        mUserIdText = (TextView) v.findViewById(R.id.fragment_post_user_id);
        mUserIdText.setText("" + mPost.getUserId());

        mPostIdText = (TextView) v.findViewById(R.id.fragment_post_post_id);
        mPostIdText.setText("" + mPost.getId());

        mTitleText = (TextView) v.findViewById(R.id.fragment_post_title_text);
        mTitleText.setText(mPost.getTitle());

        mBodyText = (TextView) v.findViewById(R.id.fragment_post_body_text);
        mBodyText.setText(mPost.getBody());

        return v;
    }
}
