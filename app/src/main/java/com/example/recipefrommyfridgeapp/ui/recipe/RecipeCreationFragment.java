package com.example.recipefrommyfridgeapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.viewmodel.LoginRegisterViewModel;
import com.example.recipefrommyfridgeapp.viewmodel.RecipeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeCreationFragment extends Fragment implements View.OnClickListener {

    private Button createRecipeButton, backButton;
    private EditText name, content, rating;
    private ProgressBar progressBar;

    private RecipeViewModel mRecipeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "RecipeCreationFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_recipe_creation, container, false);
        createRecipeButton = v.findViewById(R.id.createRecipePage);
        backButton = v.findViewById(R.id.back_generate);
        name = v.findViewById(R.id.recipe_name);
        content = v.findViewById(R.id.recipe_content);
        rating = v.findViewById(R.id.recipe_rating);
        progressBar = v.findViewById(R.id.progress_circular);
        createRecipeButton.setOnClickListener(this);
        backButton.setOnClickListener(this);


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
        if (recipeName.isEmpty()){
            name.setError("Recipe Name is required!");
            name.requestFocus();
            return;
        }
        if (recipeContent.isEmpty()){
            content.setError("Recipe Content is required!");
            content.requestFocus();
            return;
        }
        if (recipeRating.isEmpty()){
            rating.setError("Recipe Rating is required!");
            rating.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mRecipeViewModel.createRecipe(recipeName, recipeContent, Float.parseFloat(recipeRating));
        progressBar.setVisibility(View.GONE);


    }
}
