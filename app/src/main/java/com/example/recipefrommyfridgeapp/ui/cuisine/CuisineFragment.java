package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeActivity;
import com.example.recipefrommyfridgeapp.ui.recipe.RecipeCreationFragment;

public class CuisineFragment extends Fragment implements View.OnClickListener{

    private Button generateButton, createButton, backButton;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_cuisine, container, false);
        generateButton = v.findViewById(R.id.choose_cuisine_generate);
        createButton = v.findViewById(R.id.choose_cuisine_create);
        backButton = v.findViewById(R.id.choose_cuisine_back);
        generateButton.setOnClickListener(this);
        createButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

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
            case R.id.choose_cuisine_back:
//                if (getParentFragmentManager().findFragmentById(R.id.fragment_logged_in) != null){
//                    getParentFragmentManager().beginTransaction()
//                            .replace(this, R.id.fragment_logged_in)
//                            .commit();
//                    getParentFragmentManager().popBackStack();
//                }
                break;
        }

    }
}
