package com.example.recipefrommyfridgeapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.viewmodel.RecipeViewModel;

import java.util.List;

public class RecipeFragment extends Fragment implements View.OnClickListener {

    private TextView recipeName, recipeType, recipeRating, recipeContent;
    private ImageButton mPreviousButton, mNextButton;

    private RecipeViewModel mRecipeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "RecipeFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        String recipeId = (String) getActivity().getIntent()
                .getSerializableExtra(RecipeActivity.EXTRA_RECIPE_ID);
        Log.d("checkpoint5", recipeId);
        mRecipeViewModel.getCurrentRecipe(recipeId);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeName = v.findViewById(R.id.fragment_recipe_name);
        recipeType = v.findViewById(R.id.fragment_recipe_type);
        recipeRating = v.findViewById(R.id.fragment_recipe_rating);
        recipeContent = v.findViewById(R.id.fragment_recipe_content);
        mPreviousButton = v.findViewById(R.id.fragment_recipe_previous_button);
        mNextButton = v.findViewById(R.id.fragment_recipe_next_button);
        mPreviousButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mRecipeViewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null){
                    recipeName.setText("Name: " + recipe.getName());
                    recipeType.setText("Cuisine Type: " + recipe.getCuisineId());
                    recipeRating.setText("Rating: " + recipe.getRating());
                    recipeContent.setText("Content: " + recipe.getContent());
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_recipe_previous_button:
                break;
            case R.id.fragment_recipe_next_button:
                break;
        }
    }
}
