package com.example.recipefrommyfridgeapp.ui.recipe;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeFragment;

public class ChooseRecipeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ChooseRecipeFragment();
    }
}
