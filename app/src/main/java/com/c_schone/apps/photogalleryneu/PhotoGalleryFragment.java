package com.c_schone.apps.photogalleryneu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 10.04.17.
 */

public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView mPhotoRecyclerView;
    private List<Photo> mPhotoList = new ArrayList<>();

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_photo_gallery, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.fragment_photo_gallery_menu_switch_to_post_list:
                Intent intent = new Intent(getActivity(), PostListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        setupAdapter();

        return v;
    }

    private void setupAdapter() {
        if(isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mPhotoList));
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Photo mPhoto;
        private ImageView mPhotoImageView;

        public PhotoHolder(View photoView) {
            super(photoView);
            photoView.setOnClickListener(this);

            mPhotoImageView = (ImageView) photoView.findViewById(R.id.fragment_photo_gallery_list_photo_image_view);
        }

        public void bindGalleryPhoto(Photo photo) {
            mPhoto = photo;
            Glide.with(getActivity())
                    .load(mPhoto.getThumbnailUrl())
                    .into(mPhotoImageView);

        }

        @Override
        public void onClick(View view) {
            Intent intent = PhotoActivity.newIntent(getActivity(), mPhoto.getUrl());
            startActivity(intent);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<Photo> mPhotoList;

        public PhotoAdapter(List<Photo> photos) {
            mPhotoList = photos;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_photo_gallery_list_photo, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            Photo photo = mPhotoList.get(position);
            photoHolder.bindGalleryPhoto(photo);
        }

        @Override
        public int getItemCount() {
            return mPhotoList.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<Photo>> {

        @Override
        protected List<Photo> doInBackground(Void... params) {
            JSONCommunicator.get().fetchItems();
            return JSONCommunicator.get().getPhotos();
        }

        @Override
        protected void onPostExecute(List<Photo> photos) {
            mPhotoList = photos;
            setupAdapter();
        }
    }
}
