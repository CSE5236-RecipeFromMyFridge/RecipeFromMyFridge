package com.example.recipefrommyfridgeapp.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeCreationFragment extends Fragment implements View.OnClickListener {

    private Button createRecipeButton, backButton;
    private EditText name, content, rating;
    private ProgressBar progressBar;
    private FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_recipe_creation, container, false);
        createRecipeButton = v.findViewById(R.id.button_create_recipe);
        backButton = v.findViewById(R.id.back_generate);
        name = v.findViewById(R.id.recipe_name);
        content = v.findViewById(R.id.recipe_content);
        rating = v.findViewById(R.id.recipe_rating);
        progressBar = v.findViewById(R.id.progress_circular);
        createRecipeButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_generate:
                getParentFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
                getParentFragmentManager().popBackStack();
                break;
            case R.id.createRecipePage:
                createRecipe();
                break;
        }
    }

    private void createRecipe(){
        String recipeName = name.getText().toString().trim();
        String recipeContent = content.getText().toString().trim();
        String recipeRating = rating.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        Recipe recipe = new Recipe(recipeName, recipeContent, Float.parseFloat(recipeRating));
        database.getReference("Recipes").child().setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        })
    }
}
