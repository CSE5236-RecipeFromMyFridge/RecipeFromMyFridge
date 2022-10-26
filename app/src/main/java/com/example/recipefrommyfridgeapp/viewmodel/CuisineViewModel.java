package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.repository.AppRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CuisineViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;
    private MutableLiveData<List<Cuisine>> mCuisineMutableLiveData;

    public CuisineViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mCuisineMutableLiveData = mAppRepository.getCuisineMutableLiveData();
    }

    public void retrieveCuisines(){
        mAppRepository.retrieveCuisines();
    }

    public MutableLiveData<List<Cuisine>> getCuisineMutableLiveData() {
        return mCuisineMutableLiveData;
    }
}
