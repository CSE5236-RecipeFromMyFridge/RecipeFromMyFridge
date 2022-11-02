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
import com.example.recipefrommyfridgeapp.viewmodel.SavedRecipeViewModel;

import java.util.List;

public class SavedRecipeDetailsFragment extends Fragment {

    private TextView recipeName, recipeType, recipeRating, recipeContent;
    private ImageButton mPreviousButton, mNextButton;

    private SavedRecipeViewModel mRecipeViewModel;

    private String id;
    private String userId;

    public SavedRecipeDetailsFragment(String recipeId) {
        id = "" + recipeId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "RecipeFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(SavedRecipeViewModel.class);
        String recipeId = "" + id;
        Log.d("checkpoint5", recipeId);
        userId = (String) getActivity().getIntent()
                .getSerializableExtra(SavedRecipeActivity.EXTRA_USER_ID);
        Log.d("checkpoint5", "SavedRecipeDetailsFragment " + userId);
        mRecipeViewModel.getCurrentSavedRecipe(userId, recipeId);
        mRecipeViewModel.retrieveSavedRecipeIdList(userId);

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
        mRecipeViewModel.getSavedRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null){
                    recipeName.setText(String.format("Name: %s", recipe.getName()));
                    recipeType.setText(String.format("Cuisine Type: %s", recipe.getCuisineId()));
                    recipeRating.setText(String.format("Rating: %s", recipe.getRating()));
                    recipeContent.setText(String.format("Content: %s", recipe.getContent()));}
            }
        });

        mRecipeViewModel.getSavedRecipeListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> ids) {
                if (ids.size() != 0){
                    final int[] idx = {ids.indexOf(id)};
                    mPreviousButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idx[0] = (idx[0] - 1) % ids.size();
                            if (idx[0] < 0) {
                                idx[0] = ids.size() - 1;
                            }
                            updateRecipe(ids, idx);
                        }
                    });
                    mNextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idx[0] = (idx[0] + 1) % ids.size();
                            updateRecipe(ids, idx);
                        }
                    });
                }
            }
        });


        return v;
    }

    private void updateRecipe(List<String> ids, int[] idx){
        getParentFragmentManager().beginTransaction()
                .remove(SavedRecipeDetailsFragment.this)
                .commit();
        getParentFragmentManager().popBackStack();
        Fragment fragment = new SavedRecipeDetailsFragment(ids.get(idx[0]));
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setReorderingAllowed(true)
                .addToBackStack("Get saved recipe details")
                .commit();
    }
}