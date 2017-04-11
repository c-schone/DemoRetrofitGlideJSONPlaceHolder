package com.c_schone.apps.photogalleryneu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by christian on 11.04.17.
 */

public class PhotoFragment extends Fragment {

    private static final String ARG_PHOTO_URL = "photo_url";

    private Photo mPhoto;
    private ImageView mImageView;

    public static PhotoFragment newInstance(String photoUrl) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO_URL, photoUrl);

        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String photoUrl = getArguments().getString(ARG_PHOTO_URL);
        mPhoto = JSONCommunicator.get().getPhoto(photoUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);

        mImageView = (ImageView) v.findViewById(R.id.fragment_photo_image_view);

        Glide.with(getActivity())
                .load(mPhoto.getUrl())
                .into(mImageView);

        return v;
    }
}
