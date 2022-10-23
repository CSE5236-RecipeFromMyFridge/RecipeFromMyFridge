package com.example.recipefrommyfridgeapp.repository;

import android.app.Application;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class AppRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private FirebaseAuth auth;

    public AppRepository(Application application){
        this.application = application;
        auth = FirebaseAuth.getInstance();
        mUserMutableLiveData = new MutableLiveData<>();
        loggedOutMutableLiveData = new MutableLiveData<>();

        if (auth.getCurrentUser() != null){
            getUserMutableLiveData().postValue(auth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String name, String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(name, email, password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(application.getApplicationContext(), "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(application.getApplicationContext(), "(DB) Failed to register!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            mUserMutableLiveData.postValue(auth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "(Auth) Failed to register!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mUserMutableLiveData.postValue(auth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "(Auth) Failed to login!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut(){
        auth.signOut();
        loggedOutMutableLiveData.postValue(true);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }
}
