package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.repository.AppRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mUserMutableLiveData = mAppRepository.getUserMutableLiveData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String name, String email, String password){
        mAppRepository.register(name, email, password);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String name, String email){
        mAppRepository.login(name, email);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }
}
