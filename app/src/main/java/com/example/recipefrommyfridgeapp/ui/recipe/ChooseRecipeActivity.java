package com.example.recipefrommyfridgeapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;
import com.example.recipefrommyfridgeapp.ui.cuisine.CuisineFragment;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientFragment;

public class ChooseRecipeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString(CuisineFragment.INTENT_CUISINE_SELECTED,
                intent.getStringExtra(CuisineFragment.INTENT_CUISINE_SELECTED));
        bundle.putString(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED,
                intent.getStringExtra(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED));
        Fragment fragment = new ChooseRecipeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
