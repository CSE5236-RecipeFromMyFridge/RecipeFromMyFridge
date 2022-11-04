package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientFragment;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeActivity;
import com.example.recipefrommyfridgeapp.viewmodel.CuisineViewModel;

import java.util.Arrays;

public class CuisineFragment extends Fragment implements View.OnClickListener {

    public static final String INTENT_CUISINE_SELECTED = "cuisine";
    private CuisineViewModel mCuisineViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "CuisineFragment.onCreate()");
        mCuisineViewModel = new ViewModelProvider(this).get(CuisineViewModel.class);
        mCuisineViewModel.retrieveCuisines();
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_cuisine, container, false);
        Button generateButton = v.findViewById(R.id.choose_cuisine_generate);
        generateButton.setOnClickListener(this);

        mCuisineViewModel.getCuisineMutableLiveData().observe(getViewLifecycleOwner(), cuisines -> {
            if (cuisines != null) {
                for (Cuisine c : cuisines) {
                    getChildFragmentManager().beginTransaction()
                            .add(R.id.container_cuisine_item, new CuisineItemFragment(c.getName(), c.getType()))
                            .addToBackStack(null)
                            .commit();
                }
                Log.d("checkpoint5", "Successfully get Cuisines list");
            } else {
                Log.d("checkpoint5", "Fail to get Cuisine list");
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.choose_cuisine_generate) {
            String[] ingredients = getArguments().getStringArray(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED);
            String[] cuisines = mCuisineViewModel.getCuisineSelectedMutableLiveData().getValue().toArray(new String[0]);
            Log.i("test", "onClick: ingredients - " + Arrays.toString(ingredients));
            Log.i("test", "onClick: cuisines - " + Arrays.toString(cuisines));

            Intent intent = new Intent(requireContext(), ChooseRecipeActivity.class);
            intent.putExtra(INTENT_CUISINE_SELECTED, cuisines);
            intent.putExtra(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED, ingredients);
            startActivity(intent);
        }
    }
}
