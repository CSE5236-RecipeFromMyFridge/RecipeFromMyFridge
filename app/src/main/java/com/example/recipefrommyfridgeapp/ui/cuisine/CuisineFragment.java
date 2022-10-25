package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientActivity;
import com.example.recipefrommyfridgeapp.ui.login.LoggedInFragment;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeActivity;
import com.example.recipefrommyfridgeapp.ui.recipe.RecipeCreationFragment;
import com.example.recipefrommyfridgeapp.viewmodel.CuisineViewModel;

import java.util.List;

public class CuisineFragment extends Fragment implements View.OnClickListener{

    private Button generateButton, createButton;
    private TextView cuisine_1, cuisine_2, cuisine_3, cuisine_4, cuisine_5,
            cuisine_6, cuisine_7, cuisine_8, cuisine_9;
    private CuisineViewModel cuisineViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "CuisineFragment.onCreate()");
        cuisineViewModel = new ViewModelProvider(this).get(CuisineViewModel.class);
        cuisineViewModel.getCuisineMutableLiveData().observe(this, new Observer<List<Cuisine>>() {
            @Override
            public void onChanged(List<Cuisine> cuisines) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_cuisine, container, false);
        generateButton = v.findViewById(R.id.choose_cuisine_generate);
        createButton = v.findViewById(R.id.choose_cuisine_create);
        generateButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.choose_cuisine_generate:
                startActivity(new Intent(requireContext(), ChooseRecipeActivity.class));
                break;
            case R.id.choose_cuisine_create:
                Fragment fragment = new RecipeCreationFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .setReorderingAllowed(true)
                        .addToBackStack("Recipe Creation")
                        .commit();
                break;
        }

    }
}
