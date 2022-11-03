package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.repository.AppRepository;

import java.util.List;
import java.util.Map;

public class RecipeViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;
    private MutableLiveData<Recipe> mRecipeMutableLiveData;
    private MutableLiveData<List<Recipe>> recipeListMutableLiveData;
    private MutableLiveData<String> recipeIdMutableLiveData;
    private MutableLiveData<Map<String, String>> recipeIngredientsMutableLiveData;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mRecipeMutableLiveData = mAppRepository.getRecipeMutableLiveData();
        recipeListMutableLiveData = mAppRepository.getRecipeListMutableLiveData();
        recipeIdMutableLiveData = mAppRepository.getRecipeIdMutableLiveData();
        recipeIngredientsMutableLiveData = mAppRepository.getRecipeIngredientsMutableLiveData();
    }

    public void getCurrentRecipe(String recipeId){
        mAppRepository.getCurrentRecipe(recipeId);
    }

    public void returnRecipeId(String recipeName){
        mAppRepository.returnRecipeId(recipeName);
    }

    public void retrieveRecipeList() {
        mAppRepository.retrieveRecipeList();
    }

    public void retrieveRecipeIdList() {
        mAppRepository.retrieveRecipeList();
    }

    public void getRecipeIngredients(String recipeId){
        mAppRepository.getRecipeIngredients(recipeId);
    }

    public MutableLiveData<Recipe> getRecipeMutableLiveData() {
        return mRecipeMutableLiveData;
    }

    public MutableLiveData<List<Recipe>> getRecipeListMutableLiveData() {
        return recipeListMutableLiveData;
    }

    public MutableLiveData<String> getRecipeIdMutableLiveData() {
        return recipeIdMutableLiveData;
    }

    public MutableLiveData<Map<String, String>> getRecipeIngredientsMutableLiveData() {
        return recipeIngredientsMutableLiveData;
    }
}
