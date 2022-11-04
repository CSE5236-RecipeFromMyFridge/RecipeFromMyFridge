package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.repository.AppRepository;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;
import java.util.Map;

public class RecipeViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;
    private FirebaseRecyclerOptions<Recipe> options;
    private MutableLiveData<Recipe> mRecipeMutableLiveData;
    private MutableLiveData<List<String>> mRecipeListMutableLiveData;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mRecipeMutableLiveData = mAppRepository.getRecipeMutableLiveData();
        mRecipeListMutableLiveData = mAppRepository.getRecipeListMutableLiveData();
    }

    public FirebaseRecyclerOptions<Recipe> retrieveRecipes(){
        options = mAppRepository.retrieveRecipes();
        return options;
    }

    public FirebaseRecyclerOptions<Recipe> retrieveRecipes(String cuisine, String ingredient){
        options = mAppRepository.retrieveRecipes(cuisine, ingredient);
        return options;
    }

    public void getCurrentRecipe(String recipeId){
        mAppRepository.getCurrentRecipe(recipeId);
    }

    public void retrieveRecipeIdList(){
        mAppRepository.retrieveRecipeIdList();
    }

    public MutableLiveData<Recipe> getRecipeMutableLiveData() {
        return mRecipeMutableLiveData;
    }

    public MutableLiveData<List<String>> getRecipeListMutableLiveData() {
        return mRecipeListMutableLiveData;
    }
}
