package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientFragment;

public class CuisineActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putStringArray(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED,
                intent.getStringArrayExtra(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED));
        CuisineFragment cuisineFragment = new CuisineFragment();
        cuisineFragment.setArguments(bundle);
        return cuisineFragment;
    }
}