package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.repository.AppRepository;
import com.google.firebase.auth.FirebaseUser;

public class CuisineViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;

    public CuisineViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
    }

}
