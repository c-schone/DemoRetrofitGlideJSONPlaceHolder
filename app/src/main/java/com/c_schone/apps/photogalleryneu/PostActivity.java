package com.c_schone.apps.photogalleryneu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class PostActivity extends FragmentHosterActivity {

    private static final String EXTRA_USER_ID = "com.c_schone.apps.photogalleryneu.user_id";
    private static final String EXTRA_POST_ID = "com.c_schone.apps.photogalleryneu.post_id";

    public static Intent newIntent(Context packageContext, int userId, int postId) {
        Intent intent = new Intent(packageContext, PostActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_POST_ID, postId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int userId = getIntent().getIntExtra(EXTRA_USER_ID, 0);
        int postId = getIntent().getIntExtra(EXTRA_POST_ID, 0);
        return PostFragment.newInstance(userId, postId);
    }
}
