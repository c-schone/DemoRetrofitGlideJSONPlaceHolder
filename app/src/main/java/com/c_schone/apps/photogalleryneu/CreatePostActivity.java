package com.c_schone.apps.photogalleryneu;

import android.support.v4.app.Fragment;

/**
 * Created by christian on 11.04.17.
 */

public class CreatePostActivity extends FragmentHosterActivity {

    @Override
    protected Fragment createFragment() {
        return new CreatePostFragment();
    }
}
