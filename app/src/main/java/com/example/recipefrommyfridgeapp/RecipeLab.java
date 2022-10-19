package com.example.recipefrommyfridgeapp;

import android.content.Context;

import com.example.recipefrommyfridgeapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeLab {
    private static RecipeLab sRecipeLab;
    private List<Recipe> mRecipes;

    public static RecipeLab get(Context context) {
        if (sRecipeLab == null) {
            sRecipeLab = new RecipeLab(context);
        }
        return sRecipeLab;
    }

    private RecipeLab(Context context) {
        mRecipes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Recipe recipe = new Recipe();
            recipe.setName("Recipe #" + i);
            recipe.setContent("Recipe content:" + i);
            recipe.setCuisineId(i);
            recipe.setRating(i);
            mRecipes.add(recipe);
        }
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public Recipe getRecipe(String name) {
        for (Recipe recipe : mRecipes){
            if (recipe.getName().equals(name)){
                return recipe;
            }
        }
        return null;
    }

}
