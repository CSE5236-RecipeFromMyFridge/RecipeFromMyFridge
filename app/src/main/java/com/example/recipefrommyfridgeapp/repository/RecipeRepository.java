package com.example.recipefrommyfridgeapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Recipe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class RecipeRepository {
    static RecipeRepository instance;
    private List<Recipe> recipes;

    public static RecipeRepository getInstance(){
        if (instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Recipe>> getRecipes(){
        loadRecipes();
        MutableLiveData<List<Recipe>> recipe = new MutableLiveData<>();
        recipe.setValue(recipes);
        return recipe;
    }

    private void loadRecipes(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Recipe");

    }
}
