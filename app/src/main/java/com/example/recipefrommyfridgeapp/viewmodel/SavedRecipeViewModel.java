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

    public SavedRecipeViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mSavedRecipeMutableLiveData = mAppRepository.getSavedRecipeMutableLiveData();
        mSavedRecipeListMutableLiveData = mAppRepository.getSavedRecipeListMutableLiveData();
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

    public MutableLiveData<Recipe> getSavedRecipeMutableLiveData() {
        return mSavedRecipeMutableLiveData;
    }

    public MutableLiveData<List<String>> getSavedRecipeListMutableLiveData() {
        return mSavedRecipeListMutableLiveData;
    }
}
