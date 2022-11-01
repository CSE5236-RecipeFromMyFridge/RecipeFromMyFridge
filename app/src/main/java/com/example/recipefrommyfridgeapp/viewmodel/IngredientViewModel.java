package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Ingredient;
import com.example.recipefrommyfridgeapp.repository.AppRepository;

import java.util.List;
import java.util.Map;

public class IngredientViewModel extends AndroidViewModel {

    private final AppRepository mAppRepository;
    private final MutableLiveData<Map<String, List<Ingredient>>> mIngredientMutableLiveData;
    private final MutableLiveData<List<String>> mIngredientGroupMutableLiveData;

    public IngredientViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mIngredientMutableLiveData = mAppRepository.getIngredientsMutableLiveData();
        mIngredientGroupMutableLiveData = mAppRepository.getIngredientsGroupMutableLiveData();
    }

    public void retrieveIngredient() {
        mAppRepository.retrieveIngredients();
    }

    public MutableLiveData<Map<String, List<Ingredient>>> getIngredientMutableLiveData() {
        return mIngredientMutableLiveData;
    }

    public MutableLiveData<List<String>> getIngredientGroupMutableLiveData() {
        return mIngredientGroupMutableLiveData;
    }
}
