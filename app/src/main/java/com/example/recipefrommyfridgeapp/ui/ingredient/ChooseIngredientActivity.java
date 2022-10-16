package com.example.recipefrommyfridgeapp.ui.ingredient;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;

public class ChooseIngredientActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ChooseIngredientFragment();
    }
}
