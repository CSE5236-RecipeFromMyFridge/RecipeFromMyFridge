package com.example.recipefrommyfridgeapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipefrommyfridgeapp.model.Recipe;

import java.util.List;

public class RecipeViewModel extends ViewModel {
    MutableLiveData<List<Recipe>> recipes;

    public void init (Context context){
        if (recipes != null){
            return;
        }
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipes;
    }
}
