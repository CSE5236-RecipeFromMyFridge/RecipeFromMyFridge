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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientFragment;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeActivity;
import com.example.recipefrommyfridgeapp.viewmodel.CuisineViewModel;

import java.util.List;

public class CuisineFragment extends Fragment implements View.OnClickListener {

    private Button generateButton;
    private CuisineViewModel cuisineViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "CuisineFragment.onCreate()");
        cuisineViewModel = new ViewModelProvider(this).get(CuisineViewModel.class);
        cuisineViewModel.retrieveCuisines();

        String s = getArguments().getString(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED);
        Log.i("test", "onCreate: " + s);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_cuisine, container, false);
        generateButton = v.findViewById(R.id.choose_cuisine_generate);
        generateButton.setOnClickListener(this);

        cuisineViewModel.getCuisineMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Cuisine>>() {
            @Override
            //TODO: find an easier way to display it
            public void onChanged(List<Cuisine> cuisines) {
                if (cuisines != null) {
                    for (Cuisine c : cuisines) {
                        getChildFragmentManager().beginTransaction()
                                .add(R.id.container_cuisine_item, new CuisineItemFragment(c.getName() + " - " + c.getType()))
                                .addToBackStack(null)
                                .commit();
                    }
                    Log.d("checkpoint5", "Successfully get Cuisines list");
                } else {
                    Log.d("checkpoint5", "Fail to get Cuisine list");
                }
            }
        });


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_cuisine_generate:
                startActivity(new Intent(requireContext(), ChooseRecipeActivity.class));
                break;
        }
    }
}
