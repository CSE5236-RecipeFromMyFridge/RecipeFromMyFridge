package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.repository.AppRepository;

import java.util.List;

public class CuisineViewModel extends AndroidViewModel {

    private final AppRepository mAppRepository;
    private final MutableLiveData<List<Cuisine>> mCuisineMutableLiveData;
    private final MutableLiveData<String> mCuisineSelectedMutableLiveData;


    public CuisineViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mCuisineMutableLiveData = mAppRepository.getCuisineMutableLiveData();
        mCuisineSelectedMutableLiveData = new MutableLiveData<>();
    }

    public void retrieveCuisines() {
        mAppRepository.retrieveCuisines();
    }

    public MutableLiveData<List<Cuisine>> getCuisineMutableLiveData() {
        return mCuisineMutableLiveData;
    }

    public MutableLiveData<String> getCuisineSelectedMutableLiveData() {
        return mCuisineSelectedMutableLiveData;
    }

    public void setCuisineSelectedMutableLiveData(String cuisine) {
        mCuisineSelectedMutableLiveData.setValue(cuisine);
    }
}
