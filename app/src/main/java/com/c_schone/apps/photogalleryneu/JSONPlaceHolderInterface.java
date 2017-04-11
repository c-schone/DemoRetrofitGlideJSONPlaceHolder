package com.c_schone.apps.photogalleryneu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by christian on 10.04.17.
 */

public interface JSONPlaceHolderInterface {

    @GET("/posts/")
    Call<List<Post>> getAllPosts();

    @GET("/photos/")
    Call<List<Photo>> getAllPhotos();

    @POST("/posts/")
    Call<Post> createPost(@Body Post post);
}
