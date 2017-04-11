package com.c_schone.apps.photogalleryneu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by christian on 11.04.17.
 */

public class PhotoActivity extends FragmentHosterActivity {

    private static final String EXTRA_PHOTO_URL = "com.c_schone.apps.photogalleryneu.photo_url";

    public static Intent newIntent(Context packageContext, String photoURL) {
        Intent intent = new Intent(packageContext, PhotoActivity.class);
        intent.putExtra(EXTRA_PHOTO_URL, photoURL);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String photoUrl = getIntent().getStringExtra(EXTRA_PHOTO_URL);
        return PhotoFragment.newInstance(photoUrl);
    }
}
