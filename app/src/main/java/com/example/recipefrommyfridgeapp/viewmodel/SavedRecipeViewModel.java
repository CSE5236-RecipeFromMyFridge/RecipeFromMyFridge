package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.repository.AppRepository;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class SavedRecipeViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;
    private FirebaseRecyclerOptions<Recipe> options;
    private MutableLiveData<Recipe> mSavedRecipeMutableLiveData;
    private MutableLiveData<List<String>> mSavedRecipeListMutableLiveData;
    private MutableLiveData<List<String>> userSavedRecipeListMutableLiveData;

    public SavedRecipeViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mSavedRecipeMutableLiveData = mAppRepository.getSavedRecipeMutableLiveData();
        mSavedRecipeListMutableLiveData = mAppRepository.getSavedRecipeListMutableLiveData();
        userSavedRecipeListMutableLiveData = mAppRepository.getUserSavedRecipeMutableLiveData();
    }

    public FirebaseRecyclerOptions<Recipe> retrieveSavedRecipes(String userId){
        options = mAppRepository.retrieveSavedRecipes(userId);
        return options;
    }

    public void getCurrentSavedRecipe(String userId, String recipeId){
        mAppRepository.getCurrentSavedRecipe(userId, recipeId);
    }

    public void deleteSavedRecipe(String userId, String recipeId){
        mAppRepository.deleteSavedRecipe(userId, recipeId);
    }

    public void retrieveSavedRecipeIdList(String userId){
        mAppRepository.retrieveSavedRecipeIdList(userId);
    }

    public void userSavedRecipe(String userId){
        mAppRepository.userSavedRecipe(userId);
    }

    public void saveRecipe(String userId, String recipeId){
        mAppRepository.saveRecipe(userId, recipeId);
    }

    public MutableLiveData<Recipe> getSavedRecipeMutableLiveData() {
        return mSavedRecipeMutableLiveData;
    }

    public MutableLiveData<List<String>> getSavedRecipeListMutableLiveData() {
        return mSavedRecipeListMutableLiveData;
    }

    public MutableLiveData<List<String>> getUserSavedRecipeListMutableLiveData() {
        return userSavedRecipeListMutableLiveData;
    }
}
