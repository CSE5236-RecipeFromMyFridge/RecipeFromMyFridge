package com.example.recipefrommyfridgeapp.ui.recipe;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;

public class SavedRecipeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SavedRecipeFragment();
    }
}
