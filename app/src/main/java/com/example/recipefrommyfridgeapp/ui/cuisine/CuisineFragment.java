package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.ui.login.AccountCreationFragment;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeActivity;
import com.example.recipefrommyfridgeapp.ui.recipe.RecipeCreationFragment;

public class CuisineFragment extends Fragment implements View.OnClickListener{

    private Button generateButton, createButton;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_cuisine, container, false);
        generateButton = v.findViewById(R.id.button_generate_recipe);
        createButton = v.findViewById(R.id.button_create_recipe);
        generateButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_generate_recipe:
                startActivity(new Intent(requireContext(), ChooseRecipeActivity.class));
                break;
            case R.id.button_create_recipe:
                Fragment fragment = new RecipeCreationFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .setReorderingAllowed(true)
                        .addToBackStack("Account Creation")
                        .commit();
                break;
        }

    }
}
