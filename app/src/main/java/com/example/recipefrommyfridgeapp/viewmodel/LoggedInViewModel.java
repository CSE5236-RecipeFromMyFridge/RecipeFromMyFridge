package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.repository.AppRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;

    public LoggedInViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mUserMutableLiveData = mAppRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = mAppRepository.getLoggedOutMutableLiveData();
    }

    public void logOut(){
        mAppRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }
}
