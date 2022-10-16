package com.example.recipefrommyfridgeapp.ui.chooseingredient;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;
import com.example.recipefrommyfridgeapp.ui.chooseingredient.ChooseIngredientFragment;

public class ChooseIngredientActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ChooseIngredientFragment();
    }
}
