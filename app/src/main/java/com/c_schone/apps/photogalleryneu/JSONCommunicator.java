package com.c_schone.apps.photogalleryneu;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by christian on 10.04.17.
 */

public class JSONCommunicator {

    private static final String TAG = "JSONCommunicator";

    private static JSONCommunicator sJSONCommunicator;

    private JSONPlaceHolderInterface mJSONPlaceHolderInterface;
    private List<Photo> mPhotos;
    private List<Post> mPosts;

    public static JSONCommunicator get() {
        if (sJSONCommunicator == null) {
            sJSONCommunicator = new JSONCommunicator();
        }
        return sJSONCommunicator;
    }

    private JSONCommunicator() {
        mPhotos = new ArrayList<>();
        mPosts = new ArrayList<>();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        mJSONPlaceHolderInterface = retrofit.create(JSONPlaceHolderInterface.class);
    }

    public void fetchItems() {

        Call<List<Photo>> photoCall = mJSONPlaceHolderInterface.getAllPhotos();
        try {
            mPhotos = photoCall.execute().body();
            Log.d(TAG, "Gained photos from response.");
        } catch (IOException ioe) {
            Log.e(TAG, "IOException in photoCall: ", ioe);
        }

        Call<List<Post>> postCall = mJSONPlaceHolderInterface.getAllPosts();
        try {
            mPosts = postCall.execute().body();
            Log.d(TAG, "Gained posts from response.");
            Log.d(TAG, "PostID Examples: " + mPosts.get(0).getId()
                        + " " + mPosts.get(1).getId()
                        + " " + mPosts.get(2).getId());
        } catch (IOException ioe) {
            Log.e(TAG, "IOException in postCall: ", ioe);
        }
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public Photo getPhoto(String url) {
        for (Photo photo : mPhotos) {
            if (photo.getUrl().equals(url)) {
                return photo;
            }
        }
        return null;
    }

    public List<Post> getPosts() {
        return mPosts;
    }

    public Post getPost(int userId, int postId) {
        for (Post post : mPosts) {
            if (post.getUserId() == userId && post.getId() == postId) {
                return post;
            }
        }
        return null;
    }

    public void postItems(Post post) {

        Log.d(TAG, "Post UserID: " + post.getUserId() + " PostID: " + post.getId());
        Call<Post> call = mJSONPlaceHolderInterface.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post result = response.body();
                Log.d(TAG, "Resultbody: User ID: " + result.getUserId()
                            + " Post ID: " + result.getId()
                            + " Title: " + result.getTitle()
                            + " Body: " + result.getBody());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Failed to Post", t);
            }
        });
    }
}
