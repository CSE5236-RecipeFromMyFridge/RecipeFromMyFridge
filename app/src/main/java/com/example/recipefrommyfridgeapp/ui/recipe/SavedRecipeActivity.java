package com.example.recipefrommyfridgeapp.ui.recipe;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;

public class SavedRecipeActivity extends SingleFragmentActivity {

    public static final String EXTRA_USER_ID =
            "com.example.recipefrommyfridgeapp.userId";

    public static Intent newIntent(Context packageContext, String userId) {
        Intent intent = new Intent(packageContext, SavedRecipeActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new SavedRecipeFragment();
    }
}
