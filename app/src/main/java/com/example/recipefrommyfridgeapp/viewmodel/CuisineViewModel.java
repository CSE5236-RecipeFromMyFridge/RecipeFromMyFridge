package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.repository.AppRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CuisineViewModel extends AndroidViewModel {

    private final AppRepository mAppRepository;
    private final MutableLiveData<List<Cuisine>> mCuisineMutableLiveData;
    private final MutableLiveData<Set<String>> mCuisineSelectedMutableLiveData;

    public CuisineViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mCuisineMutableLiveData = mAppRepository.getCuisineMutableLiveData();
        mCuisineSelectedMutableLiveData = new MutableLiveData<>();
        mCuisineSelectedMutableLiveData.setValue(new HashSet<>());
    }

    public void retrieveCuisines() {
        mAppRepository.retrieveCuisines();
    }

    public MutableLiveData<List<Cuisine>> getCuisineMutableLiveData() {
        return mCuisineMutableLiveData;
    }

    public MutableLiveData<Set<String>> getCuisineSelectedMutableLiveData() {
        return mCuisineSelectedMutableLiveData;
    }
}
