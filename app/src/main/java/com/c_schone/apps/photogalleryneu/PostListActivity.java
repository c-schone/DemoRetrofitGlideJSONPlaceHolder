package com.c_schone.apps.photogalleryneu;

import android.support.v4.app.Fragment;

/**
 * Created by christian on 04.04.17.
 */

public class PostListActivity extends FragmentHosterActivity {

    @Override
    protected Fragment createFragment() {
        return new PostListFragment();
    }
}
