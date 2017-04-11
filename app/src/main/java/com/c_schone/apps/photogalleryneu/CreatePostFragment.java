package com.c_schone.apps.photogalleryneu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by christian on 11.04.17.
 */

public class CreatePostFragment extends Fragment {

    private static final String TAG = "CreatePostFragment";

    private Post mPost;
    private EditText mUserIdText;
    private EditText mPostIdText;
    private EditText mTitleText;
    private EditText mBodyText;
    private int userid;
    private int postid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPost = new Post();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_post, container, false);

        mUserIdText = (EditText) v.findViewById(R.id.fragment_create_post_user_id);
        mUserIdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    userid = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    Log.e(TAG, "Failed to parse String to Integer", e);
                    userid = -1;
                }
                mPost.setUserId(userid);
            }

            @Override
            public void afterTextChanged(Editable s) {
               //Left blank
            }
        });

        mPostIdText = (EditText) v.findViewById(R.id.fragment_create_post_post_id);
        mPostIdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    postid = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    Log.e(TAG, "Failed to parse String to Integer", e);
                    postid = -1;
                }
                mPost.setId(postid);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Left blank
            }
        });

        mTitleText = (EditText) v.findViewById(R.id.fragment_create_post_title_text);
        mTitleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPost.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Left blank
            }
        });

        mBodyText = (EditText) v.findViewById(R.id.fragment_create_post_body_text);
        mBodyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPost.setBody(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Left blank
            }
        });

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPost.getUserId() >= 0 && mPost.getId() >= 0 && mPost.getTitle() != null && mPost.getBody()!= null) {
            JSONCommunicator.get().postItems(mPost);
        }
    }
}
