package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Ingredient;
import com.example.recipefrommyfridgeapp.repository.AppRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IngredientViewModel extends AndroidViewModel {

    private final AppRepository mAppRepository;
    private final MutableLiveData<Map<String, List<Ingredient>>> mIngredientMutableLiveData;
    private final MutableLiveData<List<String>> mIngredientGroupMutableLiveData;
    private final MutableLiveData<Set<String>> mIngredientSelectedMutableLiveData;

    public IngredientViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mIngredientMutableLiveData = mAppRepository.getIngredientsMutableLiveData();
        mIngredientGroupMutableLiveData = mAppRepository.getIngredientsGroupMutableLiveData();
        mIngredientSelectedMutableLiveData = new MutableLiveData<>();
        mIngredientSelectedMutableLiveData.setValue(new HashSet<>());
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

    public MutableLiveData<Set<String>> getIngredientSelectedMutableLiveData() {
        return mIngredientSelectedMutableLiveData;
    }
}
