package com.example.recipefrommyfridgeapp.ui.recipe;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;

import java.util.UUID;

public class RecipeActivity extends SingleFragmentActivity {

    public static final String EXTRA_RECIPE_ID =
            "com.example.recipefrommyfridgeapp.recipe_id";

    public static Intent newIntent(Context packageContext, String recipeId) {
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new RecipeFragment();
    }
}
