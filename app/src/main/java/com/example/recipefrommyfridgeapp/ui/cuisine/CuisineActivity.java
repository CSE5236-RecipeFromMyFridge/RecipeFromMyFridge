package com.example.recipefrommyfridgeapp.ui.cuisine;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;

public class CuisineActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CuisineFragment();
    }
}