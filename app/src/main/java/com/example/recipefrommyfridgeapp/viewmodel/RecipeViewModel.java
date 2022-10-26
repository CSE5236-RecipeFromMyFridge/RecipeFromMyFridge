package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.repository.AppRepository;
import com.example.recipefrommyfridgeapp.repository.RecipeRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;
    private MutableLiveData<List<Recipe>> mRecipeListMutableData;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mRecipeListMutableData = mAppRepository.getRecipeMutableLiveData();
    }

    public void createRecipe(String name, String content, Float rating){
        mAppRepository.createRecipe(name, content, rating);
    }

    public MutableLiveData<List<Recipe>> getRecipeListMutableData() {
        return mRecipeListMutableData;
    }
}
