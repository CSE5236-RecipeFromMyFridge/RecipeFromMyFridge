    package com.example.recipefrommyfridgeapp.ui.ingredient;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;

import java.util.Locale;

    public class ChooseIngredientActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Log.i("rotation","creating ingredient fragment");
        return new ChooseIngredientFragment();
    }
}
